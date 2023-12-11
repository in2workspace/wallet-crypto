package es.in2.wallet.crypto.configuration;

import com.azure.data.appconfiguration.ConfigurationClient;
import com.azure.data.appconfiguration.ConfigurationClientBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import es.in2.wallet.crypto.configuration.properties.AzureAppConfigProperties;
import es.in2.wallet.crypto.configuration.properties.AzureKeyVaultProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AzureConfig {

    private final AzureAppConfigProperties azureAppConfigProperties;
    private final AzureKeyVaultProperties azureKeyVaultProperties;

    @Bean
    public ConfigurationClient configurationClient() {
        return new ConfigurationClientBuilder()
                .endpoint(azureAppConfigProperties.endpoint())
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
    }

    @Bean
    public SecretClient secretClient() {
        return new SecretClientBuilder()
                .vaultUrl(azureKeyVaultProperties.azureKeyVaultSecretProperties().endpoint())
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
    }

}
