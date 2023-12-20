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
import org.springframework.vault.support.VaultResponseSupport;
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
        return Mono.defer(() -> {
                    log.debug("Attempting to read from Vault with key: {}", key);
                    VaultResponseSupport<Object> response = vaultOperations.read("kv/" + key, Object.class);
                    log.debug("Response from Vault for key {}: {}", key, response);

                    if (response == null || response.getData() == null) {
                        log.debug("Secret not found for key: {}", key);
                        return Mono.error(new CredentialNotFoundException("Secret not found for key: " + key));
                    }

                    try {
                        String json = objectMapper.writeValueAsString(response.getData());
                        VaultSecretData secretData = objectMapper.readValue(json, VaultSecretData.class);
                        return Mono.justOrEmpty(secretData.privateKey());
                    } catch (Exception e) {
                        log.error("Error processing response from vault", e);
                        return Mono.error(new ParseErrorException("Error parsing vault response: " + e.getMessage()));
                    }
                })
                .doOnSuccess(secret -> log.debug("ProcessID: {} - Secret retrieved successfully for key: {}", processId, key))
                .doOnError(error -> log.error("Error retrieving secret for key {}: {}", key, error.getMessage(), error));
    }

    @Override
    public Mono<Void> deleteSecretByKey(String key) {
        String processId = MDC.get(PROCESS_ID);
        return Mono.defer(() -> {
                    log.debug("Checking if secret exists in Vault for key: {}", key);
                    VaultResponseSupport<Object> response = vaultOperations.read("kv/" + key, Object.class);

                    if (response == null) {
                        log.debug("Secret not found for key: {}, nothing to delete", key);
                        return Mono.error(new CredentialNotFoundException("Secret not found for key: " + key));
                    }

                    try {
                        log.debug("Deleting secret from Vault for key: {}", key);
                        vaultOperations.delete("kv/" + key);
                        log.debug("ProcessID: {} - Secret deleted successfully", processId);
                        return Mono.<Void>empty();
                    } catch (Exception e) {
                        log.error("Error deleting secret for key {}: {}", key, e.getMessage(), e);
                        return Mono.error(e);
                    }
                })
                .then()
                .doOnError(error -> log.error("Error occurred during deletion for key {}: {}", key, error.getMessage(), error));
    }

}
