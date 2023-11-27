package es.in2.wallet.crypto.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.cloud.azure.appconfiguration")
public record AzureAppConfigProperties(String endpoint) {
}
