package es.in2.wallet.crypto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record VaultSecretData(
        @JsonProperty("privateKey") String privateKey
) {
}
