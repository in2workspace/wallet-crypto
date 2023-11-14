package es.in2.wallet.crypto.service;

import es.in2.wallet.crypto.model.KeyDetails;
import es.in2.wallet.crypto.service.impl.CustomDidKeyServiceImpl;
import es.in2.wallet.crypto.service.impl.CustomKeyServiceImpl;
import id.walt.crypto.Key;
import id.walt.crypto.KeyAlgorithm;
import id.walt.crypto.KeyId;
import id.walt.servicematrix.ServiceMatrix;
import id.walt.services.key.KeyFormat;
import id.walt.services.key.KeyService;
import id.walt.services.keystore.KeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest(CustomDidKeyServiceImpl.class)
class CustomKeyServiceImplTest {

    private CustomKeyServiceImpl customKeyService;

    private KeyService keyService;


    @BeforeEach
    public void setUp() {
        new ServiceMatrix("service-matrix.properties");
        customKeyService = new CustomKeyServiceImpl();
        keyService = KeyService.Companion.getService();
    }

    @Test
    void createKey_ECDSA_Secp256k1() {
        KeyId keyId = keyService.generate(KeyAlgorithm.ECDSA_Secp256k1);
        Key loaded = keyService.load(keyId.getId());
        assertEquals(keyId, loaded.getKeyId());
    }
    @Test
    void createKeyAndExportPrivateKey() {
        Mono<KeyDetails> mono = customKeyService.createKeyIdAndExportPrivateKey();
        mono.subscribe(result -> {
            Key key = keyService.load(result.getKeyId().getId());
            String privateKey = keyService.export(result.getKeyId().getId(), KeyFormat.JWK, KeyType.PRIVATE);
            assertEquals(result.getKeyId().getId(), key.getKeyId().getId());
            assertEquals(result.getPrivateKey(), privateKey);
        });
    }
}
