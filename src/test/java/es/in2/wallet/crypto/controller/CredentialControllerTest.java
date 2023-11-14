package es.in2.wallet.crypto.controller;

import es.in2.wallet.crypto.service.VaultService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(CredentialController.class)
class CredentialControllerTest {

    private WebTestClient webClient;

    @MockBean
    private VaultService vaultService;

    @BeforeEach
    void setUp() {
        webClient = WebTestClient.bindToController(new CredentialController(vaultService))
                .configureClient()
                .baseUrl("/api/v1") //
                .build();
    }

    @Test
    void testGetSecret() {
        String did = "did:key:1234";

        Mockito.when(vaultService.getSecret(did))
                .thenReturn(Mono.just("private_key = 1234"));

        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/credentials")
                        .queryParam("did", did)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("private_key = 1234");
    }

    @Test
    void testDeleteSecret() {
        String did = "did:key:1234";

        Mockito.when(vaultService.deleteSecret(did))
                .thenReturn(Mono.empty());

        webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/credentials")
                        .queryParam("did", did)
                        .build())
                .exchange()
                .expectStatus().isOk();
    }
}
