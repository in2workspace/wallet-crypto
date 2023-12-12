package es.in2.wallet.crypto.configuration;

import com.azure.data.appconfiguration.ConfigurationClient;
import com.azure.data.appconfiguration.ConfigurationClientBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import es.in2.wallet.crypto.configuration.properties.AppProperties;
import es.in2.wallet.crypto.configuration.properties.AzureAppConfigProperties;
import es.in2.wallet.crypto.configuration.properties.AzureKeyVaultProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AzureConfig {

    private final AppProperties appProperties;
    private final AzureAppConfigProperties azureAppConfigProperties;
    private final AzureKeyVaultProperties azureKeyVaultProperties;

    @Bean
    public ConfigurationClient configurationClient() {
        if (appProperties.secretProvider().name().equals("azure")) {
            return new ConfigurationClientBuilder()
                    .endpoint(azureAppConfigProperties.endpoint())
                    .credential(new DefaultAzureCredentialBuilder().build())
                    .buildClient();
        } else {
            return null;
        }
    }

    @Bean
    public SecretClient secretClient() {
        if (appProperties.secretProvider().name().equals("azure")) {
            return new SecretClientBuilder()
                    .vaultUrl(azureKeyVaultProperties.azureKeyVaultSecretProperties().endpoint())
                    .credential(new DefaultAzureCredentialBuilder().build())
                    .buildClient();
        } else {
            return null;
        }
    }

}
