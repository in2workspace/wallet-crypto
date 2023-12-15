package es.in2.wallet.crypto.service;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;

public interface SignerService {
    Mono<String> signDocumentWithPrivateKey(JsonNode document, String did, String privateKey);
}
