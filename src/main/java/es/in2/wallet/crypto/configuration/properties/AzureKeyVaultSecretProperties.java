package es.in2.wallet.crypto.configuration.properties;

import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

public record AzureKeyVaultSecretProperties(String endpoint) {

    @ConstructorBinding
    public AzureKeyVaultSecretProperties(String endpoint) {
        this.endpoint = Optional.ofNullable(endpoint).orElse("https://example.vault.azure.net/");
    }

}
