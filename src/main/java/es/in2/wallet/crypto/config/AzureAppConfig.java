package es.in2.wallet.crypto.config;

import com.azure.data.appconfiguration.ConfigurationClient;
import com.azure.data.appconfiguration.ConfigurationClientBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.spring.cloud.core.properties.AzureProperties;
import es.in2.wallet.crypto.config.properties.AzureAppConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AzureAppConfig {

    private final AzureAppConfigProperties azureAppConfigProperties;

    @Bean
    public ConfigurationClient configurationClient() {
        return new ConfigurationClientBuilder()
                .endpoint(azureAppConfigProperties.endpoint())
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
    }

    /*
    @Bean
    fun azureTokenCredential(): TokenCredential {
        val credential = DefaultAzureCredentialBuilder().build()
        log.info("Token Credential: $credential")
        return credential
    }

    @Bean
    fun azureConfigurationClient(azureTokenCredential: TokenCredential): ConfigurationClient {
        log.info("ENDPOINT --> ${azureProperties.azureConfigEndpoint}")
        val configurationClient: ConfigurationClient = ConfigurationClientBuilder()
                .credential(azureTokenCredential)
                .endpoint(azureProperties.azureConfigEndpoint)
                .buildClient()
        val configValue = configurationClient.getConfigurationSetting(azureProperties.azureConfigPrefixKey + AppConfigKeys.VERIFIER_ONLINE_BASE_URL_KEY, azureProperties.azureConfigLabel)
        log.info("VALUE = $configValue")
        return configurationClient
    }
     */

}
