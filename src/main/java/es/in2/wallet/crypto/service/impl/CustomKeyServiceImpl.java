package es.in2.wallet.crypto.service.impl;

import es.in2.wallet.crypto.service.CustomKeyService;
import id.walt.crypto.KeyAlgorithm;
import id.walt.crypto.KeyId;
import id.walt.services.key.KeyService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Setter
@Slf4j
public class CustomKeyServiceImpl implements CustomKeyService {
    @Override
    public Mono<KeyId> createKeyId() {
        return Mono.fromCallable(() -> KeyService.Companion.getService().generate(KeyAlgorithm.ECDSA_Secp256k1))
                .doOnSuccess(keyId -> log.info("KeyId generated successfully: {}", keyId.getId()))
                .doOnError(throwable -> log.error("Error generating KeyId: {}", throwable.getMessage()));
    }
}
