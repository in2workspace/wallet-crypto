package es.in2.wallet.crypto.facade;

import reactor.core.publisher.Mono;

public interface DidServiceFacade {
    Mono<String> createDidKeyAndPersistIntoWalletDataAndVault(String token);
}
