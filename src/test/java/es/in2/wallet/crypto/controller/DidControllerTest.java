package es.in2.wallet.crypto.controller;

import es.in2.wallet.crypto.service.CustomDidKeyService;
import es.in2.wallet.crypto.service.DidServiceFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(DidController.class)
class DidControllerTest {

    private WebTestClient webClient;

    @MockBean
    private CustomDidKeyService customDidKeyService;

    @MockBean
    private DidServiceFacade didServiceFacade;

    @BeforeEach
    void setUp() {
        webClient = WebTestClient.bindToController(new DidController(didServiceFacade,customDidKeyService))
                .configureClient()
                .baseUrl("/api/v1") //
                .build();
    }

    @Test
    void testCreateDidKey() {
        String token = "token";

        Mockito.when(didServiceFacade.createDidKeyAndPersistIntoWalletDataAndVault(token))
                .thenReturn(Mono.just("did:key:1234"));

        webClient.post()
                .uri("/dids/key")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("did:key:1234");
    }
}

