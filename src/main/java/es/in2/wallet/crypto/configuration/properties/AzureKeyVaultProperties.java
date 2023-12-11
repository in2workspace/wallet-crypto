package es.in2.wallet.crypto.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

/**
 * AzureKeyVaultProperties
 *
 * @param azureKeyVaultSecretProperties - secret
 */
@ConfigurationProperties("spring.cloud.azure.keyvault")
public record AzureKeyVaultProperties(@NestedConfigurationProperty AzureKeyVaultSecretProperties azureKeyVaultSecretProperties) {

    @ConstructorBinding
    public AzureKeyVaultProperties(AzureKeyVaultSecretProperties azureKeyVaultSecretProperties) {
        this.azureKeyVaultSecretProperties = Optional.ofNullable(azureKeyVaultSecretProperties).orElse(new AzureKeyVaultSecretProperties(null));
    }

}
