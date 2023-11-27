package es.in2.wallet.crypto.service;

import es.in2.wallet.crypto.domain.KeyDetails;
import id.walt.crypto.KeyId;
import reactor.core.publisher.Mono;

public interface KeyGenerationService {
    Mono<KeyId> generateKey();

    Mono<KeyDetails> exportKey(KeyId keyId);
}
