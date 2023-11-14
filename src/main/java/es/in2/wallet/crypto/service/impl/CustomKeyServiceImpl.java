package es.in2.wallet.crypto.service.impl;

import es.in2.wallet.crypto.model.KeyDetails;
import es.in2.wallet.crypto.service.CustomKeyService;
import id.walt.crypto.KeyAlgorithm;
import id.walt.crypto.KeyId;
import id.walt.services.key.KeyFormat;
import id.walt.services.key.KeyService;
import id.walt.services.keystore.KeyType;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Setter
@Slf4j
public class CustomKeyServiceImpl implements CustomKeyService {
    @Override
    public Mono<KeyDetails> createKeyIdAndExportPrivateKey() {
        return Mono.fromCallable(() -> {
            KeyService keyService = KeyService.Companion.getService();
            KeyId keyId = keyService.generate(KeyAlgorithm.ECDSA_Secp256k1);
            String privateKey = keyService.export(keyId.getId(), KeyFormat.JWK, KeyType.PRIVATE);

            log.info("KeyId and private key generated successfully: {}", keyId);
            return KeyDetails.builder().privateKey(privateKey).keyId(keyId).build();
        }).doOnError(throwable -> log.error("Error generating KeyId and private key: {}", throwable.getMessage()));
    }
}
