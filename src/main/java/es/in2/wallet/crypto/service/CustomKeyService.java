package es.in2.wallet.crypto.service;

import es.in2.wallet.crypto.model.KeyDetails;
import reactor.core.publisher.Mono;

public interface CustomKeyService {

    Mono<KeyDetails> createKeyId();
}
