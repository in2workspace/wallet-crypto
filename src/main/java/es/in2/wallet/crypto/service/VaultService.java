package es.in2.wallet.crypto.service;

import reactor.core.publisher.Mono;

public interface VaultService {
    Mono<Void> saveSecret(String did, String privateKeyString);
    Mono<String> getSecret(String did);
    Mono<Void> deleteSecret(String did);
}
