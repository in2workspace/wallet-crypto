package es.in2.wallet.crypto.service;

import reactor.core.publisher.Mono;

public interface CustomDidKeyService {

    Mono<Void> createDidKey(String userToken);
    Mono<String> createDidKeyJwkJcsPub();

}
