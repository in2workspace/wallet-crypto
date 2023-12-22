package es.in2.wallet.crypto.configuration.properties;

import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

/**
 * SecretProvider
 *
 * @param name - secret provider name
 */
public record AppSecretProviderProperties(String name) {

    @ConstructorBinding
    public AppSecretProviderProperties(String name) {
        this.name = Optional.ofNullable(name).orElse("hashicorp");
    }

}
