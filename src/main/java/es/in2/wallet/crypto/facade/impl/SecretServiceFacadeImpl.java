package es.in2.wallet.crypto.facade.impl;

import es.in2.wallet.crypto.config.properties.AppProperties;
import es.in2.wallet.crypto.facade.SecretServiceFacade;
import es.in2.wallet.crypto.service.AzureKeyVaultStorageService;
import es.in2.wallet.crypto.service.HashiCorpVaultStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static es.in2.wallet.crypto.util.Utils.PROCESS_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecretServiceFacadeImpl implements SecretServiceFacade {

    private final AppProperties appProperties;
    private final HashiCorpVaultStorageService hashiCorpVaultStorageService;
    private final AzureKeyVaultStorageService azureKeyVaultStorageService;

    @Override
    public Mono<String> getSecretByDID(String did) {
        String processId = MDC.get(PROCESS_ID);
        if (appProperties.secretProvider().name().equals("hashicorp")) {
            return hashiCorpVaultStorageService.getSecretByKey(did)
                    .doOnSuccess(secret -> log.info("ProcessId: {} - Secret retrieved successfully", processId))
                    .doOnError(Mono::error);
        } else if (appProperties.secretProvider().name().equals("azure")) {
            return azureKeyVaultStorageService.getSecretByKey(did)
                    .doOnSuccess(secret -> log.info("ProcessId: {} - Secret retrieved successfully", processId))
                    .doOnError(Mono::error);
        } else {
            return Mono.error(new Exception("Secret provider not supported"));
        }
    }

    @Override
    public Mono<Void> deleteSecretByDID(String did) {
        String processId = MDC.get(PROCESS_ID);
        if (appProperties.secretProvider().name().equals("hashicorp")) {
            return hashiCorpVaultStorageService.deleteSecretByKey(did)
                    .doOnSuccess(secret -> log.info("ProcessId: {} - Secret deleted successfully", processId))
                    .doOnError(Mono::error);
        } else if (appProperties.secretProvider().name().equals("azure")) {
            return azureKeyVaultStorageService.deleteSecretByKey(did)
                    .doOnSuccess(secret -> log.info("ProcessId: {} - Secret deleted successfully", processId))
                    .doOnError(Mono::error);
        } else {
            return Mono.error(new Exception("Secret provider not supported"));
        }
    }

}
