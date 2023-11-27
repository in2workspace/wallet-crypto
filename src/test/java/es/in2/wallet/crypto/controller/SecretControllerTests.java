package es.in2.wallet.crypto.controller;

import es.in2.wallet.crypto.facade.SecretServiceFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest(controllers = SecretController.class)
@ExtendWith(SpringExtension.class)
class SecretControllerTests {

    private static final String DID_PARAM = "did:example:123456";

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private SecretServiceFacade secretServiceFacade;

    @Test
    void testGetSecretByDID() {
        String expectedSecret = "someSecretValue";
        Mockito.when(secretServiceFacade.getSecretByDID(DID_PARAM))
                .thenReturn(Mono.just(expectedSecret));
        webTestClient.get()
                .uri("/api/v1/secrets?did={did}", DID_PARAM)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> {
                    assertEquals(expectedSecret, response);
                });
    }

//    @Test
//    void testGetSecretByDIDNotFound() {
//        Mockito.when(secretServiceFacade.getSecretByDID(DID_PARAM))
//                .thenReturn(Mono.error(new Exception()));
//        webTestClient.get()
//                .uri("/api/v1/secrets?did={did}", DID_PARAM)
//                .exchange()
//                .expectStatus().isNotFound();
//    }

    @Test
    void testDeleteSecretByDID() {
        Mockito.when(secretServiceFacade.deleteSecretByDID(DID_PARAM))
                .thenReturn(Mono.empty());
        webTestClient.delete()
                .uri("/api/v1/secrets?did={did}", DID_PARAM)
                .exchange()
                .expectStatus().isNoContent();
    }

//    @Test
//    void testDeleteSecretByDIDNotFound() {
//        Mockito.when(secretServiceFacade.deleteSecretByDID(DID_PARAM))
//                .thenReturn(Mono.error(new Exception()));
//        webTestClient.delete()
//                .uri("/api/v1/secrets?did={did}", DID_PARAM)
//                .exchange()
//                .expectStatus().isNotFound();
//    }

}
