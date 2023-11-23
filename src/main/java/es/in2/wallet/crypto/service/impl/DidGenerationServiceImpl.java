package es.in2.wallet.crypto.service.impl;

import es.in2.wallet.crypto.service.DidGenerationService;
import id.walt.crypto.KeyId;
import id.walt.model.DidMethod;
import id.walt.services.did.DidKeyCreateOptions;
import id.walt.services.did.DidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class DidGenerationServiceImpl implements DidGenerationService {

    private static final DidService didService = DidService.INSTANCE;

    @Override
    public Mono<String> createDidKey(KeyId keyId) {
        String processId = MDC.get("processId");
        return Mono.fromCallable(() -> didService.create(DidMethod.key, keyId.getId(), null))
                .doOnSuccess(did -> log.info("ProcessID: {} - DID created successfully: {}", processId, did))
                .doOnError(throwable -> log.error("ProcessID: {} - Error creating DID: {}", processId, throwable.getMessage()));
    }

    @Override
    public Mono<String> createDidKeyJwkJcsPub(KeyId keyId) {
        String processId = MDC.get("processId");
        return Mono.fromCallable(() -> didService.create(DidMethod.key, keyId.getId(), new DidKeyCreateOptions(true)))
                .doOnSuccess(did -> log.info("ProcessID: {} - DID created successfully: {}", processId, did))
                .doOnError(throwable -> log.error("ProcessID: {} - Error creating DID: {}", processId, throwable.getMessage()));
    }

}
