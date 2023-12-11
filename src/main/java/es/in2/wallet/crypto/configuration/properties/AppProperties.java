package es.in2.wallet.crypto.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

/**
 * AppProperties
 *
 * @param secretProvider - secret provider
 */
@ConfigurationProperties(prefix = "app")
public record AppProperties(@NestedConfigurationProperty AppSecretProviderProperties secretProvider) {

    @ConstructorBinding
    public AppProperties(AppSecretProviderProperties secretProvider) {
        this.secretProvider = Optional.ofNullable(secretProvider).orElse(new AppSecretProviderProperties(null));
    }

}
