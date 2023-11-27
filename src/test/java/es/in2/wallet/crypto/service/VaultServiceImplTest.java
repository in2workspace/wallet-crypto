//package es.in2.wallet.crypto.service;
//
//import es.in2.wallet.crypto.service.impl.VaultServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
//import org.springframework.vault.core.VaultOperations;
//import org.springframework.vault.support.VaultResponse;
//import org.springframework.vault.support.VaultResponseSupport;
//import reactor.test.StepVerifier;
//
//
//import java.util.Map;
//
//import static org.mockito.ArgumentMatchers.eq;
//
//@WebFluxTest(VaultServiceImplTest.class)
//public class VaultServiceImplTest {
//    @Mock
//    private VaultOperations vaultOperations;
//
//    @InjectMocks
//    private VaultServiceImpl vaultService;
//
//    @Test
//    void saveSecret_Success() {
//        // Arrange
//        String did = "testDid";
//        String privateKey = "testPrivateKey";
//        VaultResponse mockVaultResponse = new VaultResponse(); // Asegúrate de que esto sea una instancia válida
//        Mockito.when(vaultOperations.write(eq("kv/" + did), Mockito.any())).thenReturn(mockVaultResponse);
//
//        // Act & Assert
//        StepVerifier.create(vaultService.saveSecret(did, privateKey))
//                .verifyComplete();
//
//        Mockito.verify(vaultOperations, Mockito.times(1)).write(eq("kv/" + did), Mockito.any());
//    }
//
//
//
//    @Test
//    void getSecret_Success(){
//        // Arrange
//        String did = "testDid";
//        String expectedPrivateKey = "1234";
//        Map<String, String> expectedResponse = Map.of("privateKey",expectedPrivateKey);
//
//        VaultResponseSupport<Object> response = new VaultResponseSupport<>();
//        response.setData(expectedResponse);
//        Mockito.when(vaultOperations.read("kv/" + did, Object.class)).thenReturn(response);
//
//        // Act & Assert
//        StepVerifier.create(vaultService.getSecret(did))
//                .expectNext(expectedPrivateKey)
//                .verifyComplete();
//    }
//
//    @Test
//    void deleteSecret_Success() {
//        // Arrange
//        String did = "testDid";
//        VaultResponseSupport<Object> response = new VaultResponseSupport<>();
//        response.setData(new Object());
//
//        Mockito.when(vaultOperations.read("kv/" + did, Object.class)).thenReturn(response);
//        Mockito.doNothing().when(vaultOperations).delete("kv/" + did);
//
//        // Act & Assert
//        StepVerifier.create(vaultService.deleteSecret(did))
//                .verifyComplete();
//
//        Mockito.verify(vaultOperations, Mockito.times(1)).delete("kv/" + did);
//    }
//}
