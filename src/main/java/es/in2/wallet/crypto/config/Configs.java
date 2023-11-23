package es.in2.wallet.crypto.config;

import es.in2.wallet.crypto.config.properties.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class Configs {

    private final AppProperties appProperties;
    private final OpenApiProperties openApiProperties;
    private final AzureKeyVaultProperties azureKeyVaultProperties;
    private final HashiCorpVaultProperties hashiCorpVaultProperties;
    private final WalletDataProperties walletDataProperties;

    @PostConstruct
    void init() {
        log.info("Configurations uploaded: ");
        log.info("... {}", appProperties);
        log.info("... {}", openApiProperties.server());
        log.info("... {}", openApiProperties.info());
        log.info("... {}", azureKeyVaultProperties);
        log.info("... {}", hashiCorpVaultProperties);
        log.info("... {}", walletDataProperties);
    }

}
