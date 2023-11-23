package es.in2.wallet.crypto.facade.impl;

import es.in2.wallet.crypto.service.HashiCorpVaultStorageService;
import es.in2.wallet.crypto.facade.SecretServiceFacade;
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

    private final HashiCorpVaultStorageService hashiCorpVaultStorageService;

    @Override
    public Mono<String> getSecretByDID(String did) {
        String processId = MDC.get(PROCESS_ID);
        return hashiCorpVaultStorageService.getSecretByKey(did)
                .doOnSuccess(secret -> log.info("ProcessId: {} - Secret retrieved successfully", processId))
                .doOnError(Mono::error);
    }

    @Override
    public Mono<Void> deleteSecretByDID(String did) {
        String processId = MDC.get(PROCESS_ID);
        return hashiCorpVaultStorageService.deleteSecretByKey(did)
                .doOnSuccess(secret -> log.info("ProcessId: {} - Secret deleted successfully", processId))
                .doOnError(Mono::error);
    }

}
