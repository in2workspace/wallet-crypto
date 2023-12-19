package es.in2.wallet.crypto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.wallet.crypto.domain.VaultSecretData;
import es.in2.wallet.crypto.exception.ParseErrorException;
import es.in2.wallet.crypto.service.impl.HashiCorpVaultStorageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.VaultResponse;
import org.springframework.vault.support.VaultResponseSupport;
import reactor.test.StepVerifier;

import javax.security.auth.login.CredentialNotFoundException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HashiCorpVaultStorageServiceImplTest {
    @InjectMocks
    private HashiCorpVaultStorageServiceImpl hashiCorpVaultStorageServiceImpl;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private VaultOperations vaultOperations;

    @Test
    void saveSecretSuccess() {
        when(vaultOperations.write(anyString(), any())).thenReturn(new VaultResponse());

        StepVerifier.create(hashiCorpVaultStorageServiceImpl.saveSecret("testKey", "testSecret"))
                .verifyComplete();
    }

    @Test
    void saveSecretFailure() {
        when(vaultOperations.write(anyString(), any())).thenThrow(new RuntimeException("Error"));

        StepVerifier.create(hashiCorpVaultStorageServiceImpl.saveSecret("testKey", "testSecret"))
                .expectError(RuntimeException.class)
                .verify();
    }
    @Test
    void getSecretByKeySuccess() throws JsonProcessingException {
        VaultResponseSupport<Object> mockResponse = new VaultResponseSupport<>();
        VaultSecretData data = new VaultSecretData("testSecret");
        mockResponse.setData(data);
        when(vaultOperations.read(anyString(), eq(Object.class))).thenReturn(mockResponse);
        when(objectMapper.writeValueAsString(any())).thenReturn("{\"privateKey\":\"testSecret\"}");
        when(objectMapper.readValue(anyString(), eq(VaultSecretData.class))).thenReturn(data);

        StepVerifier.create(hashiCorpVaultStorageServiceImpl.getSecretByKey("testKey"))
                .expectNext("testSecret")
                .verifyComplete();
    }

    @Test
    void getSecretByKeyNotFound() {
        when(vaultOperations.read(anyString(), eq(Object.class))).thenReturn(null);

        StepVerifier.create(hashiCorpVaultStorageServiceImpl.getSecretByKey("testKey"))
                .expectError(CredentialNotFoundException.class)
                .verify();
    }

    @Test
    void getSecretByKeyParseError() throws JsonProcessingException {
        VaultResponseSupport<Object> mockResponse = new VaultResponseSupport<>();
        mockResponse.setData(new Object());
        when(vaultOperations.read(anyString(), eq(Object.class))).thenReturn(mockResponse);
        when(objectMapper.writeValueAsString(any())).thenThrow(new JsonProcessingException("Error") {});

        StepVerifier.create(hashiCorpVaultStorageServiceImpl.getSecretByKey("testKey"))
                .expectError(ParseErrorException.class)
                .verify();
    }

    @Test
    void deleteSecretByKeySuccess() {
        VaultResponseSupport<Object> mockResponse = new VaultResponseSupport<>();
        mockResponse.setData(new Object());
        when(vaultOperations.read(anyString(), eq(Object.class))).thenReturn(mockResponse);
        doNothing().when(vaultOperations).delete(anyString());

        StepVerifier.create(hashiCorpVaultStorageServiceImpl.deleteSecretByKey("testKey"))
                .verifyComplete();
    }

    @Test
    void deleteSecretByKeyNotFound() {
        when(vaultOperations.read(anyString(), eq(Object.class))).thenReturn(null);

        StepVerifier.create(hashiCorpVaultStorageServiceImpl.deleteSecretByKey("testKey"))
                .expectError(CredentialNotFoundException.class)
                .verify();
    }

    @Test
    void deleteSecretByKeyFailure() {
        VaultResponseSupport<Object> mockResponse = new VaultResponseSupport<>();
        mockResponse.setData(new Object());
        when(vaultOperations.read(anyString(), eq(Object.class))).thenReturn(mockResponse);
        doThrow(new RuntimeException("Error")).when(vaultOperations).delete(anyString());

        StepVerifier.create(hashiCorpVaultStorageServiceImpl.deleteSecretByKey("testKey"))
                .expectError(RuntimeException.class)
                .verify();
    }
}
