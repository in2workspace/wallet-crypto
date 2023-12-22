package es.in2.wallet.crypto.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.wallet.crypto.facade.impl.SignerServiceFacadeImpl;
import es.in2.wallet.crypto.service.HashiCorpVaultStorageService;
import es.in2.wallet.crypto.service.SignerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignerServiceFacadeImplTest {
    @Mock
    private HashiCorpVaultStorageService hashiCorpVaultStorageService;
    @Mock
    private SignerService signerService;
    @InjectMocks
    private SignerServiceFacadeImpl signerServiceFacade;

    @Test
    void testSignDocument() throws JsonProcessingException {
        when(hashiCorpVaultStorageService.getSecretByKey(anyString())).thenReturn(Mono.just("private key"));
        when(signerService.signDocumentWithPrivateKey(any(),anyString(),anyString(),anyString())).thenReturn(Mono.just("signed document"));
        String json = "{\"document\":\"sign this document\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);

        StepVerifier.create(signerServiceFacade.signDocument(jsonNode, "did:example", "proof"))
                .expectNext("signed document")
                .verifyComplete();
    }

}
