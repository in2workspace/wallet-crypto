package es.in2.wallet.crypto.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record DidRequestDTO(
        @JsonProperty("did") String did,
        @JsonProperty("didType") String didType
) {
}
