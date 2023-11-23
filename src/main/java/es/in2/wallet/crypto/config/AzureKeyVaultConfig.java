package es.in2.wallet.crypto.config;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import es.in2.wallet.crypto.config.properties.AzureKeyVaultProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AzureKeyVaultConfig {

    private final AzureKeyVaultProperties azureKeyVaultProperties;

    @Bean
    public SecretClient secretClient() {
        return new SecretClientBuilder()
                .vaultUrl(azureKeyVaultProperties.secret().endpoint())
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
    }

}
