package es.in2.wallet.crypto.service;

import es.in2.wallet.crypto.service.impl.CustomDidKeyServiceImpl;
import id.walt.crypto.KeyAlgorithm;
import id.walt.crypto.KeyId;
import id.walt.model.DidMethod;
import id.walt.services.did.DidService;
import id.walt.services.key.KeyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomDidKeyServiceImplTest {

    private CustomDidKeyServiceImpl didKeyService;
    private KeyService keyService;
    private DidService didService;

    @BeforeEach
    void setUp() {
        didKeyService = mock(CustomDidKeyServiceImpl.class);
        keyService = mock(KeyService.class);
        didService = mock(DidService.class);
    }

    @Test
    void testCreateDidKey() {
        // Mock the behavior of KeyService and DidService
        KeyId keyId = new KeyId("mockKeyId");
        when(keyService.generate(KeyAlgorithm.ECDSA_Secp256k1)).thenReturn(keyId);

        String expectedDidKey = "mockDidKey";
        when(didService.create(DidMethod.key, keyId.getId(), null)).thenReturn(expectedDidKey);

        // Invoke the method
        Mono<String> result = didKeyService.createDidKey();

        // Verify the result using StepVerifier
        StepVerifier.create(result)
                .expectNext(expectedDidKey)
                .expectComplete()
                .verify();
    }

//    @Test
//    public void testCreateDidKeyJwkJcsPub() {
//        // Create a mock for KeyService
//        KeyService keyService = Mockito.mock(KeyService.class);
//        when(keyService.generate(KeyAlgorithm.ECDSA_Secp256r1)).thenReturn(new KeyId("mockKeyId2"));
//
//        // Create a mock for DidService
//        DidService didService = Mockito.mock(DidService.class);
//        when(didService.create(DidMethod.key, "mockKeyId2", new DidKeyCreateOptions(true))).thenReturn("mockDidKey2");
//
//        CustomDidKeyServiceImpl didKeyService = new CustomDidKeyServiceImpl();
//        didKeyService.setKeyService(keyService);
//        didKeyService.setDidService(didService);
//
//        Mono<String> result = didKeyService.createDidKeyJwkJcsPub();
//
//        StepVerifier.create(result)
//                .expectNext("mockDidKey2")
//                .expectComplete()
//                .verify();
//    }

}

