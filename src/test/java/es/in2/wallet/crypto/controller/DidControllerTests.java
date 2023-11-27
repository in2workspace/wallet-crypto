package es.in2.wallet.crypto.controller;

import es.in2.wallet.crypto.facade.DidServiceFacade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest(controllers = DidController.class)
@ExtendWith(SpringExtension.class)
class DidControllerTests {

    private static final String BEARER_TOKEN_SAMPLE = "Bearer eyJraWQiOiJkaWQ6a2V5OnpEbmFlalFYUGltdEpGVmlZYUVYOXJ0WGJMdEI1OXFjVlhuVEdNeTMyNXc5OExVMlQiLCJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJzdWIiOiI1M2M0ZDg5ZC02MmQ4LTQzMmItYTdhYy05ODIxNDI5ZGI4OTYiLCJhdWQiOiJodHRwczovL3NlbGYtaXNzdWVkLm1lL3YyIiwicm9sZXMiOlsiVVNFUiJdLCJpc3MiOiJkaWQ6a2V5OnpEbmFlalFYUGltdEpGVmlZYUVYOXJ0WGJMdEI1OXFjVlhuVEdNeTMyNXc5OExVMlQiLCJleHAiOjE2OTI5NjQ4MDksImlhdCI6MTY5Mjk1ODgwOSwiZW1haWwiOiJ0ZXN0MUBleGFtcGxlIiwidXNlcm5hbWUiOiJ0ZXN0MSJ9.AHdMI7m3asMJk687yRjcjuL_81sL10JJLWiHW0LzJpj-SNIPPrqkDrdEGsTAViJU1VF12dNNWYEV-xPYnEWlYA";
    ;
    private static final String DID_SAMPLE = "did:key:zQ3shoAiqthQMSMrT9hS5Mg9f9G7gRpgwK1PxCRQbaFwKjfH2";

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private DidServiceFacade didServiceFacade;

    @Test
    void testCreateDidKey() {
        Mockito.when(didServiceFacade.createDidKeyAndPersistIntoWalletDataAndVault(Mockito.anyString()))
                .thenReturn(Mono.just(DID_SAMPLE));
        webTestClient.post()
                .uri("/api/v1/dids/key")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN_SAMPLE)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> {
                    assertEquals(DID_SAMPLE, response);
                });
    }

    @Test
    void testCreateDidKeyJwkJcsPub() {
        Mockito.when(didServiceFacade.createDidKeyAndPersistIntoWalletDataAndVault(Mockito.anyString()))
                .thenReturn(Mono.just(DID_SAMPLE));
        webTestClient.post()
                .uri("/api/v1/dids/key/jwk-jcs-pub")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN_SAMPLE)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .value(Assertions::assertNull);
    }

    @Test
    void testInvalidAuthorizationHeader() {
        Mockito.when(didServiceFacade.createDidKeyAndPersistIntoWalletDataAndVault(Mockito.anyString()))
                .thenReturn(Mono.just(DID_SAMPLE));
        webTestClient.post()
                .uri("/api/v1/dids/key")
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(String.class)
                .value(response -> {
                    // assertions
                });
    }

}
