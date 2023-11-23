package es.in2.wallet.crypto.config.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

@Slf4j
@ConfigurationProperties(prefix = "app")
public record AppProperties(@NestedConfigurationProperty SecretProvider secretProvider) {

    @ConstructorBinding
    public AppProperties(SecretProvider secretProvider) {
        this.secretProvider = Optional.ofNullable(secretProvider).orElse(new SecretProvider(null));
    }
}
