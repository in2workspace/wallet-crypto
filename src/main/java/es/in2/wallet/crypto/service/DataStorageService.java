package es.in2.wallet.crypto.service;

import reactor.core.publisher.Mono;

public interface DataStorageService {
    Mono<Void> saveDidKey(String token, String did);
}
