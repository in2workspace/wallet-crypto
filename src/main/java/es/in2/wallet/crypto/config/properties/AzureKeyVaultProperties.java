package es.in2.wallet.crypto.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

@ConfigurationProperties("spring.cloud.azure.keyvault")
public record AzureKeyVaultProperties(@NestedConfigurationProperty Secret secret) {

    @ConstructorBinding
    public AzureKeyVaultProperties(Secret secret) {
        this.secret = Optional.ofNullable(secret).orElse(new Secret(null));
    }

}
