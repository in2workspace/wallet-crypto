package es.in2.wallet.crypto.configuration;

import es.in2.wallet.crypto.configuration.properties.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AppConfigs {

    private final AppProperties appProperties;
    private final OpenApiProperties openApiProperties;
    private final AzureKeyVaultProperties azureKeyVaultProperties;
    private final AzureAppConfigProperties azureAppConfigProperties;
    private final HashiCorpVaultProperties hashiCorpVaultProperties;
    private final WalletDataProperties walletDataProperties;

    @PostConstruct
    void init() {
        log.debug("Configurations uploaded: ");
        log.debug("App Properties: {}", appProperties);
        log.debug("OpenAPI Properties: {}", openApiProperties);
        log.debug("Azure Key Vault Properties: {}", azureKeyVaultProperties);
        log.debug("Azure App Config Properties: {}", azureAppConfigProperties);
        log.debug("HashiCorp Vault Properties: {}", hashiCorpVaultProperties);
        log.debug("Wallet Data Properties: {}", walletDataProperties);
    }

}
