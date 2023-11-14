package es.in2.wallet.crypto.service;

import es.in2.wallet.crypto.service.impl.CustomDidKeyServiceImpl;
import id.walt.crypto.KeyAlgorithm;
import id.walt.crypto.KeyId;
import id.walt.model.Did;
import id.walt.model.DidMethod;
import id.walt.servicematrix.ServiceMatrix;
import id.walt.services.did.DidKeyCreateOptions;
import id.walt.services.did.DidService;
import id.walt.services.key.KeyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import reactor.core.publisher.Mono;


import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest(CustomDidKeyServiceImpl.class)
class CustomDidServiceImplTest {

    private CustomDidKeyServiceImpl customDidKeyService;

    private KeyService keyService;
    private DidService didService;


    @BeforeEach
    public void setUp() {
        new ServiceMatrix("service-matrix.properties");
        customDidKeyService = new CustomDidKeyServiceImpl();
        keyService = KeyService.Companion.getService();
        didService = DidService.INSTANCE;
    }

    @Test
    void createDefaultDidKey() {
        String did = didService.create(DidMethod.key, null, null);
        Did loaded = didService.load(did);
        assertEquals(did, loaded.getId());
    }

    @Test
    void createEdDSA_Ed25519DidKey() {
        KeyId keyId = keyService.generate(KeyAlgorithm.EdDSA_Ed25519);
        String did = didService.create(DidMethod.key, keyId.getId(), null);
        Did loaded = didService.load(did);
        assertEquals(did, loaded.getId());
    }

    @Test
    void createECDSA_Secp256r1DidKey() {
        KeyId keyId = keyService.generate(KeyAlgorithm.ECDSA_Secp256r1);
        String did = didService.create(DidMethod.key, keyId.getId(), null);
        Did loaded = didService.load(did);
        assertEquals(did, loaded.getId());
    }

    @Test
    void createECDSA_Secp256k1DidKey() {
        KeyId keyId = keyService.generate(KeyAlgorithm.ECDSA_Secp256k1);
        String did = didService.create(DidMethod.key, keyId.getId(), null);
        Did loaded = didService.load(did);
        assertEquals(did, loaded.getId());
    }

    @Test
    void createRSADidKey() {
        KeyId keyId = keyService.generate(KeyAlgorithm.RSA);
        String did = didService.create(DidMethod.key, keyId.getId(), null);
        Did loaded = didService.load(did);
        assertEquals(did, loaded.getId());
    }

    @Test
    void createJwkJcsPubDidKey() {
        KeyId keyId = keyService.generate(KeyAlgorithm.ECDSA_Secp256r1);
        DidKeyCreateOptions options = new DidKeyCreateOptions(true);
        String did = didService.create(DidMethod.key, keyId.getId(), options);
        Did loaded = didService.load(did);
        assertEquals(did, loaded.getId());
    }
    @Test
    void createDidKey() {
        KeyId keyId = keyService.generate(KeyAlgorithm.ECDSA_Secp256k1);
        Mono<String> mono = customDidKeyService.createDidKey(keyId);
        mono.subscribe(result -> {
            Did loaded = didService.load(result);
            assertEquals(result, loaded.getId());
        });
    }

    @Test
    void createDidKeyJwkJcsPub() {
        Mono<String> mono = customDidKeyService.createDidKeyJwkJcsPub();
        mono.subscribe(result -> {
            Did loaded = didService.load(result);
            assertEquals(result, loaded.getId());
        });
    }


}

