package es.in2.wallet.crypto.controller;

import es.in2.wallet.crypto.service.CustomDidKeyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(DidController.class)
class DidControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private CustomDidKeyService customDidKeyService;

    @Test
    void testCreateDid() {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJxOGFyVmZaZTJpQkJoaU56RURnT3c3Tlc1ZmZHNElLTEtOSmVIOFQxdjJNIn0.eyJleHAiOjE2OTg3NTg0NjcsImlhdCI6MTY5ODc1ODE2NywianRpIjoiMjljOGFlY2UtMmRmZi00NTZkLTk3OGItM2Y5MDk0MDJkOGFkIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDg0L3JlYWxtcy9XYWxsZXRJZFAiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiYWViNWY4NDktMzkwOS00OGQzLTk3MDItYTc4MzY3YmEyNGY1IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoid2FsbGV0LWNsaWVudCIsInNlc3Npb25fc3RhdGUiOiJmY2Q5Y2MwYS1iOTQ3LTRmM2UtYjgxZC01ODNjMjgwNjg3MWMiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6NDIwMCJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy13YWxsZXRpZHAiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiZmNkOWNjMGEtYjk0Ny00ZjNlLWI4MWQtNTgzYzI4MDY4NzFjIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhbnRvbmlvIiwiZW1haWwiOiJhbnRvbmlvQGV4YW1wbGUuY29tIn0.fFG9tgRQvOPmk7lLRLbuiLU5tO-zr7a2frV2aumBxD_EqA5FeadWH_u90ZOgDJzmGk0jJNihwoDmJqJLIivs16l6R9bGZONJLEc2aw64J1IaMeJo0rHkIMpXx7Vf7BnKLwo1Jj1pvEsbmhNdYxj6PEYyUtqucwFqbYMp01bGFhKFmbVjMv39RvnVTK-HPk2wceAKEDogbXPnIQ9bhF2uJktWmAxhyFqv1Ll59HwcqDuSVaE32ka59K5OMWt6oOyYIxxaWfMFqRh3aEvbMm8HYV2tUq75uonpAR3K_I9OapCBb2BYhirbP3Vvx4MQYhQ90EBUPEWN14Sa0ic9xdTSIw";


        Mockito.when(customDidKeyService.createDidKey(token))
                .thenReturn(Mono.empty());

        webClient.post()
                .uri("/api/v1/dids/key")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchange()
                .expectStatus().isCreated();
    }
}
