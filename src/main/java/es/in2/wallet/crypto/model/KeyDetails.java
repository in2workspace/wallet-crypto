package es.in2.wallet.crypto.model;

import id.walt.crypto.KeyId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class KeyDetails {
    private KeyId keyId;
    private String privateKey;
}
