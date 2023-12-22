package es.in2.wallet.crypto.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.wallet.crypto.domain.SignRequest;
import es.in2.wallet.crypto.facade.SignerServiceFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignerControllerTests {
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private SignerServiceFacade signerServiceFacade;
    @InjectMocks
    private  SignerController signerController;



    @Test
    void testGetEntityById() throws JsonProcessingException {
        String json = "{\"document\":\"sign this document\"}";
        JsonNode jsonNode = objectMapper.readTree(json);
        SignRequest signRequest = SignRequest.builder()
                .document(jsonNode)
                .did("did:example")
                .documentType("proof")
                .build();
        when(signerServiceFacade.signDocument(any(), anyString(),anyString())).thenReturn(Mono.just("signed"));
        // Act & Assert
        WebTestClient
                .bindToController(signerController)
                .build()
                .post()
                .uri("/api/v2/sign")
                .bodyValue(signRequest)
                .exchange()
                .expectStatus().isCreated();
    }

}
