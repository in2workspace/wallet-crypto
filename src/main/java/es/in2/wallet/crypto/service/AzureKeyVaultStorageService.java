package es.in2.wallet.crypto.service;

import reactor.core.publisher.Mono;

public interface AzureKeyVaultStorageService {

    Mono<Void> saveSecret(String key, String secret);

    Mono<String> getSecretByKey(String key);

    Mono<Void> deleteSecretByKey(String key);
}
