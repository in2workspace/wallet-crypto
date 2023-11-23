package es.in2.wallet.crypto.config;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureKeyVaultConfig {

    @Value("${spring.cloud.azure.keyvault.secret.endpoint}")
    private String keyVaultSecretEndpoint;

    @Bean
    public SecretClient secretClient() {
        return new SecretClientBuilder()
                .vaultUrl(keyVaultSecretEndpoint)
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
    }

}
