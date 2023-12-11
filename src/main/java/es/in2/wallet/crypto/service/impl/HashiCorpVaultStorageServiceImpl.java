package es.in2.wallet.crypto.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.wallet.crypto.domain.VaultSecretData;
import es.in2.wallet.crypto.exception.ParseErrorException;
import es.in2.wallet.crypto.service.HashiCorpVaultStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultOperations;
import reactor.core.publisher.Mono;

import javax.security.auth.login.CredentialNotFoundException;

import static es.in2.wallet.crypto.utils.Utils.PROCESS_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashiCorpVaultStorageServiceImpl implements HashiCorpVaultStorageService {

    private final ObjectMapper objectMapper;
    private final VaultOperations vaultOperations;

    @Override
    public Mono<Void> saveSecret(String key, String secret) {
        String processId = MDC.get(PROCESS_ID);
        return Mono.fromCallable(() -> vaultOperations.write("kv/" + key,
                        VaultSecretData.builder().privateKey(secret).build()))
                .then()
                .doOnSuccess(voidValue -> log.debug("ProcessID: {} - Secret saved successfully", processId))
                .onErrorResume(Exception.class, Mono::error);
    }

    @Override
    public Mono<String> getSecretByKey(String key) {
        String processId = MDC.get(PROCESS_ID);
        return Mono.fromCallable(() -> vaultOperations.read("kv/" + key, Object.class))
                .flatMap(response -> {
                    try {
                        // Read data from response to get the secret
                        if (response != null && response.getData() != null) {
                            String json = objectMapper.writeValueAsString(response.getData());
                            String secret = objectMapper.readValue(json, VaultSecretData.class).privateKey();
                            return Mono.just(secret);
                        } else {
                            return Mono.error(new CredentialNotFoundException("Secret not found"));
                        }
                    } catch (Exception e) {
                        return Mono.error(new ParseErrorException("Vault response could not be parsed"));
                    }
                })
                .doOnSuccess(voidValue -> log.debug("ProcessID: {} - Secret retrieved successfully", processId))
                .doOnError(error -> log.error("Error retrieving secret: {}", error.getMessage(), error));
    }

    @Override
    public Mono<Void> deleteSecretByKey(String key) {
        String processId = MDC.get(PROCESS_ID);
        return Mono.fromRunnable(() -> vaultOperations.delete("kv/" + key))
                .then()
                .doOnSuccess(voidValue -> log.debug("ProcessID: {} - Secret deleted successfully", processId))
                .onErrorResume(Exception.class, Mono::error);
    }

}
