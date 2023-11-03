package es.in2.wallet.crypto.service;

import reactor.core.publisher.Mono;

public interface DidServiceFacade {
    Mono<String> createDidKeyAndPersistIntoWalletData(String token);
}
