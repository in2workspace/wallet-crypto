package es.in2.wallet.crypto.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;

@Builder
public record SignRequest(
        @JsonProperty("did") String did,
        @JsonProperty("document") JsonNode document
) {
}