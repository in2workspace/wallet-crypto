package es.in2.wallet.crypto.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("azure.keyvault")
public record AzureKeyVaultProperties(String vaultUrl) {
}
