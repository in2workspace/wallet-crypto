package es.in2.wallet.crypto.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wallet-data")
public record WalletDataProperties(String url) {
}
