package es.in2.wallet.crypto.service;

import es.in2.wallet.crypto.service.impl.CustomDidKeyServiceImpl;
import es.in2.wallet.crypto.util.ApplicationUtils;
import id.walt.crypto.KeyAlgorithm;
import id.walt.crypto.KeyId;
import id.walt.model.Did;
import id.walt.model.DidMethod;
import id.walt.services.did.DidKeyCreateOptions;
import id.walt.services.did.DidService;
import id.walt.services.key.KeyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
class CustomDidKeyServiceImplTest {

    @Autowired
    private CustomDidKeyServiceImpl customDidKeyService;

    @MockBean
    private ApplicationUtils applicationUtils;

    @Value("${wallet-data.url}")
    private String walletDataUrl;
    private KeyService keyService;
    private DidService didService;


    @BeforeEach
    public void setUp() {
        customDidKeyService = new CustomDidKeyServiceImpl(applicationUtils,walletDataUrl);
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
        Mockito.when(applicationUtils.postRequest(eq(walletDataUrl), anyList(), anyString()))
                .thenReturn(Mono.empty());
        String userToken = "test-token";
        customDidKeyService.createDidKey(userToken).block();

        Mockito.verify(applicationUtils).postRequest(eq(walletDataUrl), anyList(), anyString());
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

