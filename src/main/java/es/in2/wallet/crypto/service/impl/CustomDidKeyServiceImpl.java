package es.in2.wallet.crypto.service.impl;

import es.in2.wallet.crypto.service.CustomDidKeyService;
import id.walt.crypto.KeyAlgorithm;
import id.walt.crypto.KeyId;
import id.walt.model.DidMethod;
import id.walt.services.did.DidKeyCreateOptions;
import id.walt.services.did.DidService;
import id.walt.services.key.KeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CustomDidKeyServiceImpl implements CustomDidKeyService {

    public Mono<String> createDidKey() {
        KeyId keyId = KeyService.Companion.getService().generate(KeyAlgorithm.ECDSA_Secp256k1);
        Mono<String> didKey = Mono.just(DidService.INSTANCE.create(DidMethod.key, keyId.getId(), null))
                .doOnSuccess(result -> log.info("Success: {}", result))
                .doOnError(throwable -> log.error("Error: {}", throwable.getMessage()));
        // todo save result to db using Wallet Data
        return didKey;
    }

    public Mono<String> createDidKeyJwkJcsPub() {
        KeyId keyId = KeyService.Companion.getService().generate(KeyAlgorithm.ECDSA_Secp256r1);
        Mono<String> didKey = Mono.just(DidService.INSTANCE.create(DidMethod.key, keyId.getId(), new DidKeyCreateOptions(true)))
                .doOnSuccess(result -> log.info("Success: {}", result))
                .doOnError(throwable -> log.error("Error: {}", throwable.getMessage()));
        // todo save result to db using Wallet Data
        return didKey;
    }

}
