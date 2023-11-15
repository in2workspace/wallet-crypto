package es.in2.wallet.crypto.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.wallet.crypto.model.KeySecret;
import es.in2.wallet.crypto.service.VaultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.VaultResponseSupport;
import reactor.core.publisher.Mono;

import javax.security.auth.login.CredentialNotFoundException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class VaultServiceImpl implements VaultService {
    private final VaultOperations vaultOperations;

    @Override
    public Mono<Void> saveSecret(String did, String privateKey) {
        return convertStringToSecret(privateKey)
                .flatMap(keySecret -> Mono.fromRunnable(() -> vaultOperations.write("kv/" + did, keySecret)))
                .then()
                .onErrorResume(Exception.class, Mono::error);
    }

    @Override
    public Mono<String> getSecret(String did) {
        VaultResponseSupport<Object> response = vaultOperations.read("kv/" + did, Object.class);
        if (response == null || response.getData() == null) {
            return Mono.error(new CredentialNotFoundException("Secret not found for DID: " + did));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(response.getData());
            JsonNode jsonNode = objectMapper.readTree(json);
            if (!jsonNode.has("privateKey")) {
                return Mono.error(new CredentialNotFoundException("Private key not found for DID: " + did));
            }
            return Mono.just(jsonNode.get("privateKey").asText());
        } catch (IOException e) {
            return Mono.error(e);
        }
    }


    @Override
    public Mono<Void> deleteSecret(String did) {
        return Mono.defer(() -> {
            VaultResponseSupport<Object> response = vaultOperations.read("kv/" + did, Object.class);
            if (response == null || response.getData() == null) {
                return Mono.error(new CredentialNotFoundException("Secret not found for DID: " + did));
            }
            return Mono.fromRunnable(() -> vaultOperations.delete("kv/" + did)).then();
        }).onErrorResume(Exception.class, e -> {
            log.error("Error deleting secret: {}", e.getMessage());
            return Mono.error(new CredentialNotFoundException("Error deleting secret for DID: " + did));
        });
    }




    private Mono<KeySecret> convertStringToSecret(String privateKeyString) {
        return Mono.just(KeySecret.builder().privateKey(privateKeyString).build());
    }
}
