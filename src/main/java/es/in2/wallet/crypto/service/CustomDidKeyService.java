package es.in2.wallet.crypto.service;

import reactor.core.publisher.Mono;

public interface CustomDidKeyService {

    Mono<String> createDidKey();
    Mono<String> createDidKeyJwkJcsPub();

}
