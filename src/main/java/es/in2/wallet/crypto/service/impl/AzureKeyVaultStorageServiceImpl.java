package es.in2.wallet.crypto.service.impl;

import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.azure.security.keyvault.secrets.models.SecretProperties;
import es.in2.wallet.crypto.service.AzureKeyVaultStorageService;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

import static es.in2.wallet.crypto.utils.Utils.PROCESS_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AzureKeyVaultStorageServiceImpl implements AzureKeyVaultStorageService {

    private final SecretClient secretClient;


    @Override
    public Mono<Void> saveSecret(@NonNull String key, @NotNull String secret) {
        String processId = MDC.get(PROCESS_ID);
        log.info("Key: {} - Secret: {}", key, secret);
        KeyVaultSecret newSecret = new KeyVaultSecret(
                parseDidUriToAzureKeyVaultSecretName(key),
                secret)
                .setProperties(new SecretProperties()
                        .setExpiresOn(OffsetDateTime.now().plusDays(60))
                        .setContentType("application/json"));
        return Mono.fromCallable(() ->
                        secretClient.setSecret(newSecret))
                .then()
                .doOnSuccess(voidValue -> log.info("ProcessID: {} - Secret saved successfully", processId))
                .onErrorResume(Exception.class, Mono::error);
    }

    @Override
    public Mono<String> getSecretByKey(String key) {
        String processId = MDC.get(PROCESS_ID);
        return Mono.fromCallable(() -> {
                    try {
                        return secretClient.getSecret(parseDidUriToAzureKeyVaultSecretName(key)).getValue();
                    } catch (Exception e) {
                        return "Communication with Key Vault failed: " + e;
                    }
                })
                .doOnSuccess(voidValue -> log.info("ProcessID: {} - Secret retrieved successfully", processId))
                .onErrorResume(Exception.class, Mono::error);
    }

    @Override
    public Mono<Void> deleteSecretByKey(String key) {
        String processId = MDC.get(PROCESS_ID);
        return Mono.fromRunnable(() -> {
                    try {
                        secretClient.beginDeleteSecret(parseDidUriToAzureKeyVaultSecretName(key));
                    } catch (Exception e) {
                        log.error("ProcessID: {} - Failed to delete secret: {}", processId, e.getMessage());
                    }
                })
                .then()
                .doOnSuccess(voidValue -> log.info("ProcessID: {} - Secret deleted successfully", processId))
                .onErrorResume(Exception.class, Mono::error);
    }

    private String parseDidUriToAzureKeyVaultSecretName(String key) {
        return key.replace(":", "-");
    }

}
