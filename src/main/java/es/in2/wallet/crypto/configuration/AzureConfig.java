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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static es.in2.wallet.crypto.utils.Utils.AZURE;

@Configuration
@RequiredArgsConstructor
public class AzureConfig {

    private final AppProperties appProperties;
    private final AzureAppConfigProperties azureAppConfigProperties;
    private final AzureKeyVaultProperties azureKeyVaultProperties;

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "secretProvider", havingValue = "hashicorp")
    public ConfigurationClient configurationClient() {
        if (appProperties.secretProvider().name().equals(AZURE)) {
            return new ConfigurationClientBuilder()
                    .endpoint(azureAppConfigProperties.endpoint())
                    .credential(new DefaultAzureCredentialBuilder().build())
                    .buildClient();
        } else {
            return null;
        }
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "secretProvider", havingValue = "hashicorp")
    public SecretClient secretClient() {
        if (appProperties.secretProvider().name().equals(AZURE)) {
            return new SecretClientBuilder()
                    .vaultUrl(azureKeyVaultProperties.azureKeyVaultSecretProperties().endpoint())
                    .credential(new DefaultAzureCredentialBuilder().build())
                    .buildClient();
        } else {
            return null;
        }
    }

}
