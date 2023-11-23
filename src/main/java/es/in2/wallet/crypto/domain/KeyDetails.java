package es.in2.wallet.crypto.domain;

import id.walt.crypto.KeyId;
import lombok.Builder;

@Builder
public record KeyDetails(
        KeyId keyId,
        String privateKey
) {
}
