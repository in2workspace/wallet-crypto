package es.in2.wallet.crypto.facade;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;

public interface SignerServiceFacade {
    Mono<String> signDocument(JsonNode document, String did);
}
