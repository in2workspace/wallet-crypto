package es.in2.wallet.crypto.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

/**
 * AzureAppConfigProperties
 *
 * @param endpoint - azure app configuration endpoint
 */
@ConfigurationProperties("spring.cloud.azure.appconfiguration")
public record AzureAppConfigProperties(String endpoint) {

    @ConstructorBinding
    public AzureAppConfigProperties(String endpoint) {
        this.endpoint = Optional.ofNullable(endpoint).orElse("https://domain.azconfig.io");
    }

}
