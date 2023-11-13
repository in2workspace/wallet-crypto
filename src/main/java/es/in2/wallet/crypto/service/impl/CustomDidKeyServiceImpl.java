package es.in2.wallet.crypto.service.impl;

import es.in2.wallet.crypto.service.CustomDidKeyService;
import id.walt.crypto.KeyAlgorithm;
import id.walt.crypto.KeyId;
import id.walt.model.DidMethod;
import id.walt.services.did.DidKeyCreateOptions;
import id.walt.services.did.DidService;
import id.walt.services.key.KeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomDidKeyServiceImpl implements CustomDidKeyService {

    @Override
    public Mono<String> createDidKey(KeyId keyId) {
        return Mono.just(keyId)
                .flatMap(kid -> Mono.just(DidService.INSTANCE.create(DidMethod.key, kid.getId(), null)))
                .doOnSuccess(did -> log.info("DID created successfully: {}", did))
                .doOnError(throwable -> log.error("Error creating DID: {}", throwable.getMessage()));
    }

    @Override
    public Mono<String> createDidKeyJwkJcsPub() {
        KeyId keyId = KeyService.Companion.getService().generate(KeyAlgorithm.ECDSA_Secp256r1);
        return Mono.just(DidService.INSTANCE.create(DidMethod.key, keyId.getId(), new DidKeyCreateOptions(true)))
                .doOnSuccess(result -> log.info("Success: {}", result))
                .doOnError(throwable -> log.error("Error: {}", throwable.getMessage()));
    }

}
