//package es.in2.wallet.crypto.service.impl;
//
//import com.azure.security.keyvault.secrets.SecretClient;
//import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
//import es.in2.wallet.crypto.exception.ParseErrorException;
//import es.in2.wallet.crypto.service.AzureKeyVaultStorageService;
//import es.in2.wallet.crypto.service.CryptographicStorageService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.MDC;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import javax.security.auth.login.CredentialNotFoundException;
//
//import static es.in2.wallet.crypto.util.Utils.PROCESS_ID;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//@Qualifier("keyVaultStorageService")
//public class AzureKeyVaultStorageServiceImpl implements AzureKeyVaultStorageService {
//
//    private final SecretClient secretClient;
//
//    @Override
//    public Mono<Void> saveSecret(String key, String secret) {
//        String processId = MDC.get(PROCESS_ID);
//        return Mono.fromCallable(() ->
//                        secretClient.setSecret(new KeyVaultSecret(key, secret)))
//                .then()
//                .doOnSuccess(voidValue -> log.info("ProcessID: {} - Secret saved successfully", processId))
//                .onErrorResume(Exception.class, Mono::error);
//    }
//
//    @Override
//    public Mono<String> getSecretByKey(String key) {
//        String processId = MDC.get(PROCESS_ID);
//        return Mono.fromCallable(() ->
//                        secretClient.getSecret(key))
//                .flatMap(keyVaultSecret -> {
//                    try {
//                        // Read data from response to get the secret
//                        if (keyVaultSecret != null && keyVaultSecret.getValue() != null) {
//                            return Mono.just(keyVaultSecret.getValue());
//                        } else {
//                            return Mono.error(new CredentialNotFoundException("Secret not found"));
//                        }
//                    } catch (Exception e) {
//                        return Mono.error(new ParseErrorException("Vault response could not be parsed"));
//                    }
//                })
//                .doOnSuccess(voidValue -> log.info("ProcessID: {} - Secret retrieved successfully", processId))
//                .doOnError(error -> log.error("Error retrieving secret: {}", error.getMessage(), error));
//    }
//
//    @Override
//    public Mono<Void> deleteSecretByKey(String key) {
//        String processId = MDC.get(PROCESS_ID);
//        return Mono.fromRunnable(() ->
//                        secretClient.beginDeleteSecret(key))
//                .then()
//                .doOnSuccess(voidValue -> log.info("ProcessID: {} - Secret deleted successfully", processId))
//                .onErrorResume(Exception.class, Mono::error);
//    }
//
//}
