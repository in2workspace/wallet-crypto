package es.in2.wallet.crypto.config;

import es.in2.wallet.crypto.config.properties.AzureKeyVaultProperties;
import es.in2.wallet.crypto.config.properties.HashiCorpVaultProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.vault.config.VaultProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MySecretConfig {

    private final HashiCorpVaultProperties hashiCorpVaultProperties;
    private final AzureKeyVaultProperties azureKeyVaultProperties;

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "secret-provider", havingValue = "hashicorp")
    public VaultProperties vaultProperties() {
        VaultProperties vaultProperties = new VaultProperties();
        vaultProperties.setAuthentication(VaultProperties.AuthenticationMethod .TOKEN);
        vaultProperties.setToken(hashiCorpVaultProperties.token());
        vaultProperties.setHost(hashiCorpVaultProperties.host());
        vaultProperties.setPort(hashiCorpVaultProperties.port());
        vaultProperties.setScheme(hashiCorpVaultProperties.scheme());
        return vaultProperties;
    }

//    @Bean
//    @ConditionalOnProperty(prefix = "app", name = "secret-provider", havingValue = "azure")
//    public DefaultAzureCredential defaultAzureCredential() {
//        // Configure the credential chain with Managed Identity and Azure CLI
//        return new DefaultAzureCredentialBuilder().build();
//    }
//
//    @Bean
//    @ConditionalOnProperty(prefix = "app", name = "secret-provider", havingValue = "azure")
//    public SecretClient secretClient() {
//        // Azure SDK client builders accept the credential as a parameter
//        return new SecretClientBuilder()
//                .vaultUrl(azureKeyVaultProperties.vaultUrl())
//                .credential(defaultAzureCredential())
//                .buildClient();
//    }

}
