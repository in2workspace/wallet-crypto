package es.in2.wallet.crypto.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.wallet.crypto.util.ApplicationUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Configuration
@RequiredArgsConstructor
public class VaultConfig {
    // URLs for Vault initialization and unsealing
    private static final String VAULT_INIT_URL = "http://localhost:8200/v1/sys/init";
    private static final String VAULT_UNSEAL_URL = "http://localhost:8200/v1/sys/unseal";
    private static final String VAULT_HEALTH_URL = "http://localhost:8200/v1/sys/health";
    // Paths to files where keys and tokens are stored
    private static final String KEYS_FILE_PATH = "keys.txt";
    private static final String TOKEN_FILE_PATH = "token.txt";


    private final ApplicationUtils applicationUtils;
    @Tag(name = "Vault", description = "Unseal the vault")
    @Bean
    public void unsealVault() throws JsonProcessingException {
        // Check if the keys file exists to determine the initialization state
        if (!keysFileExists()){
            // Initialize Vault and store keys and token
            String initResponse = applicationUtils.postRequest(VAULT_INIT_URL, new ArrayList<>(), "{\"secret_shares\": 5, \"secret_threshold\": 3}").block();
            Map<String, Object> parsedResponse = parseVaultInitResponse(initResponse);
            saveKeysToFile(parsedResponse);
            saveRootTokenToFile(parsedResponse);
            // Unseal Vault with the new keys
            List<String> keys = readKeysFromFile();
            for (String key : keys) {
                unsealWithKey(key);
            }
        }
        else{
            // Read unseal keys from file
            List<String> keys = readKeysFromFile();
            // Use the keys to unseal Vault
            String healthResponse = applicationUtils.getRequest(VAULT_HEALTH_URL, new ArrayList<>()).block();
            // Convert health response to a map to check if Vault is sealed
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> healthMap = objectMapper.readValue(healthResponse, new TypeReference<>() {});
            boolean isSealed = (Boolean) healthMap.get("sealed");
            if (isSealed){
                // Unseal Vault using the keys
                for (String key : keys) {
                    unsealWithKey(key);
                }
            }
        }
    }
    // Bean to set up Vault properties in the Spring environment
//    @Bean
//    @DependsOn("unsealVault")
//    public Properties vaultProperties(ConfigurableEnvironment environment) {
//        Properties vaultProperties = new Properties();
//        Path tokenPath = Paths.get(TOKEN_FILE_PATH);
//        if (Files.exists(tokenPath)) {
//            try {
//                // Read the token from the file, assuming the file contains only the token in a single line
//                String rootToken = new String(Files.readAllBytes(tokenPath)).trim();
//
//                // Set the token property in the Properties object
//                vaultProperties.setProperty("cloud.vault.token", rootToken);
//
//                // Add the root token to the Spring environment
//                MutablePropertySources propertySources = environment.getPropertySources();
//                propertySources.addFirst(new MapPropertySource("vaultTokenPropertySource", Collections.singletonMap("cloud.vault.token", rootToken)));
//            } catch (IOException e) {
//                throw new RuntimeException("Error reading vault token from file", e);
//            }
//        } else {
//            throw new IllegalStateException("Vault token file not found");
//        }
//        return vaultProperties;
//    }
    // Method to unseal Vault with a given key
    private void unsealWithKey(String key) {
        // Create payload for unsealing
        String unsealPayload = "{\"key\": \"" + key + "\"}";
        // Send unseal request to Vault
        applicationUtils.postRequest(VAULT_UNSEAL_URL, new ArrayList<>(), unsealPayload).block();
    }

    // Method to save keys to a file
    private void saveKeysToFile(Map<String, Object> vaultCredentials) {
        @SuppressWarnings("unchecked")
        List<String> keysBase64 = (List<String>) vaultCredentials.get("keys_base64");

        Path keysPath = Paths.get(KEYS_FILE_PATH);
        try {
            if (Files.notExists(keysPath)) {
                Files.createFile(keysPath);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(keysPath.toFile()))) {
                for (String key : keysBase64) {
                    writer.write(key);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving keys to file", e);
        }
    }

    // Method to save the root token to a file
    private void saveRootTokenToFile(Map<String, Object> vaultCredentials) {
        String rootToken = (String) vaultCredentials.get("root_token");

        Path tokenPath = Paths.get(TOKEN_FILE_PATH);
        try {
            if (Files.notExists(tokenPath)) {
                Files.createFile(tokenPath);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tokenPath.toFile()))) {
                writer.write(rootToken);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving root token to file", e);
        }
    }

    // Method to read the keys from a file
    private List<String> readKeysFromFile() {
        try {
            Path path = Paths.get(KEYS_FILE_PATH);
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Error reading keys from file", e);
        }
    }

    // Method to parse vault response
    private Map<String, Object> parseVaultInitResponse(String initResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
            return objectMapper.readValue(initResponse, typeRef);
        } catch (IOException e) {
            throw new RuntimeException("Error parsing init response from Vault", e);
        }
    }
    private boolean keysFileExists() {
        return Files.exists(Paths.get(KEYS_FILE_PATH));
    }
}
