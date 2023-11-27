//package es.in2.wallet.crypto.facade;
//
//import es.in2.wallet.crypto.config.properties.AppProperties;
//import es.in2.wallet.crypto.config.properties.SecretProvider;
//import es.in2.wallet.crypto.domain.KeyDetails;
//import es.in2.wallet.crypto.facade.impl.DidServiceFacadeImpl;
//import es.in2.wallet.crypto.service.*;
//import id.walt.crypto.KeyId;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import reactor.core.publisher.Mono;
//import reactor.test.StepVerifier;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//
//@WebFluxTest
//@ExtendWith(SpringExtension.class)
//class DidServiceFacadeImplTests {
//
//    private static final String BEARER_TOKEN_SAMPLE = "Bearer eyJraWQi...";
//
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @MockBean
//    private AppProperties appProperties;
//
//    @MockBean
//    private KeyGenerationService keyGenerationService;
//
//    @MockBean
//    private DidGenerationService didGenerationService;
//
//    @MockBean
//    private HashiCorpVaultStorageService hashiCorpVaultStorageService;
//
//    @MockBean
//    private AzureKeyVaultStorageService azureKeyVaultStorageService;
//
//    @MockBean
//    private DataStorageService dataStorageService;
//
//    @Autowired
//    private DidServiceFacadeImpl didServiceFacade;
//
//    @BeforeEach
//    void setUp() {
//        KeyId keyId = new KeyId("sampleKeyId");
//        // Mock behavior for AppProperties
//        when(appProperties.secretProvider()).thenReturn(new SecretProvider("hashicorp"));
//        // Mock behavior for KeyGenerationService
//        when(keyGenerationService.generateKey())
//                .thenReturn(Mono.just(keyId));
//        // Mock behavior for DidGenerationService
//        when(didGenerationService.createDidKey(keyId))
//                .thenReturn(Mono.just("did:sample:123"));
//        when(didGenerationService.createDidKeyJwkJcsPub(keyId))
//                .thenReturn(Mono.just("did:sample:456"));
//        // Mock behavior for HashiCorpVaultStorageService and AzureKeyVaultStorageService
//        when(hashiCorpVaultStorageService.saveSecret(anyString(), anyString()))
//                .thenReturn(Mono.empty());
//        when(azureKeyVaultStorageService.saveSecret(anyString(), anyString()))
//                .thenReturn(Mono.empty());
//        // Mock behavior for DataStorageService
//        when(dataStorageService.saveDidKey(eq(BEARER_TOKEN_SAMPLE), anyString()))
//                .thenReturn(Mono.empty());
//    }
//
//    @Test
//    void testCreateDidKeyAndPersistIntoWalletDataAndVault_Hashicorp() {
//        // Mocking
////        KeyId keyId = new KeyId("sampleKeyId");
////        String privateKey = "samplePrivateKey";
//        String did = "did:sample:123";
////        when(appProperties.secretProvider().name()).thenReturn("hashicorp");
////        when(keyGenerationService.generateKey()).thenReturn(Mono.just(keyId));
////        when(didGenerationService.createDidKey(keyId)).thenReturn(Mono.just(did));
////        when(hashiCorpVaultStorageService.saveSecret(did, privateKey)).thenReturn(Mono.empty());
////        when(dataStorageService.saveDidKey(BEARER_TOKEN_SAMPLE, did)).thenReturn(Mono.empty());
//        // Test
//        Mono<String> result = didServiceFacade.createDidKeyAndPersistIntoWalletDataAndVault(BEARER_TOKEN_SAMPLE);
//        StepVerifier.create(result)
//                .expectNext(did)
//                .verifyComplete();
//    }
//
//    @Test
//    void testCreateDidKeyJwkJcsPubAndPersistIntoWalletDataAndVault_Azure() {
//        // Mocking
//        KeyId keyId = new KeyId("sampleKeyId");
//        String privateKey = "samplePrivateKey";
//        String did = "did:sample:456";
//        when(appProperties.secretProvider().name()).thenReturn("azure");
//        when(keyGenerationService.generateKey()).thenReturn(Mono.just(keyId));
//        when(didGenerationService.createDidKeyJwkJcsPub(keyId)).thenReturn(Mono.just(did));
//        when(azureKeyVaultStorageService.saveSecret(did, privateKey)).thenReturn(Mono.empty());
//        when(dataStorageService.saveDidKey(BEARER_TOKEN_SAMPLE, did)).thenReturn(Mono.empty());
//        // Test
//        Mono<String> result = didServiceFacade.createDidKeyJwkJcsPubAndPersistIntoWalletDataAndVault(BEARER_TOKEN_SAMPLE);
//        StepVerifier.create(result)
//                .expectNext(did)
//                .verifyComplete();
//    }
//
//    @Test
//    void testCreateDidKeyAndPersistIntoWalletDataAndVault_UnsupportedSecretProvider() {
//        // Mocking
//        when(appProperties.secretProvider().name()).thenReturn(null);
//        // Test
//        Mono<String> result = didServiceFacade.createDidKeyAndPersistIntoWalletDataAndVault(BEARER_TOKEN_SAMPLE);
//        StepVerifier.create(result)
//                .expectErrorMatches(throwable ->
//                        throwable instanceof Exception &&
//                                throwable.getMessage().equals("Secret provider not supported"))
//                .verify();
//    }
//
//}
//
