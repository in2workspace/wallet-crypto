package es.in2.wallet.crypto.service;

import id.walt.crypto.KeyId;
import reactor.core.publisher.Mono;

public interface CustomDidKeyService {

    Mono<String> createDidKey(KeyId keyId);
    Mono<String> createDidKeyJwkJcsPub();

}
