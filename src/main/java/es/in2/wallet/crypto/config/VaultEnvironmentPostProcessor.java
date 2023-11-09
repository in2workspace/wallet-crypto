package es.in2.wallet.crypto.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class VaultEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String VAULT_TOKEN_PROPERTY = "cloud.vault.token";
    private static final String TOKEN_FILE_PATH = "token.txt";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String token = environment.getProperty(VAULT_TOKEN_PROPERTY);
        if (token == null || token.isEmpty()) {
            Path tokenPath = Paths.get(TOKEN_FILE_PATH);
            if (Files.exists(tokenPath)) {
                try {
                    token = new String(Files.readAllBytes(tokenPath)).trim();
                    environment.getPropertySources().addFirst(
                            new MapPropertySource("vaultTokenPropertySource", Collections.singletonMap(VAULT_TOKEN_PROPERTY, token))
                    );
                } catch (IOException e) {
                    throw new IllegalStateException("Failed to read Vault token from file", e);
                }
            } else {
                throw new IllegalStateException("Vault token file not found");
            }
        }
    }
}

