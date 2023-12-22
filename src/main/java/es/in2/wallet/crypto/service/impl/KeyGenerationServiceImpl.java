package es.in2.wallet.crypto.service.impl;

import es.in2.wallet.crypto.domain.KeyDetails;
import es.in2.wallet.crypto.service.KeyGenerationService;
import id.walt.crypto.KeyAlgorithm;
import id.walt.crypto.KeyId;
import id.walt.services.key.KeyFormat;
import id.walt.services.key.KeyService;
import id.walt.services.keystore.KeyType;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class KeyGenerationServiceImpl implements KeyGenerationService {

    private final KeyService keyService = KeyService.Companion.getService();

    @Override
    public Mono<KeyId> generateKey() {
        String processId = MDC.get("processId");
        return Mono.fromCallable(() -> keyService.generate(KeyAlgorithm.ECDSA_Secp256r1))
                .doOnSuccess(keyId -> log.debug("ProcessID: {} - Key generated: {}", processId, keyId.getId()))
                .doOnError(throwable -> log.error("ProcessID: {} - Error generating KeyId and private key: {}", processId, throwable.getMessage()));
    }

    @Override
    public Mono<KeyDetails> exportKey(KeyId keyId) {
        String processId = MDC.get("processId");
        return Mono.fromCallable(() -> {
                    String privateKey = keyService.export(keyId.getId(), KeyFormat.JWK, KeyType.PRIVATE);
                    return KeyDetails.builder().privateKey(privateKey).keyId(keyId).build();
                })
                .doOnSuccess(keyDetails -> log.debug("ProcessID: {} - Key exported: {}", processId, keyDetails.keyId().getId()))
                .doOnError(throwable -> log.error("ProcessID: {} - Error exporting KeyId and private key: {}", processId, throwable.getMessage()));
    }

}
