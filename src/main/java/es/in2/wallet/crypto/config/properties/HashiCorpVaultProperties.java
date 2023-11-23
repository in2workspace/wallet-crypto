package es.in2.wallet.crypto.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.cloud.vault")
public record HashiCorpVaultProperties(String authentication, String token, String scheme,
                                       String host, int port) {
}
