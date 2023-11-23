package es.in2.wallet.crypto.service;

import reactor.core.publisher.Mono;

public interface SecretServiceFacade {
    Mono<String> getSecretByDID(String did);
    Mono<Void> deleteSecretByDID(String did);
}
