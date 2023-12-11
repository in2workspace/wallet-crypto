package es.in2.wallet.crypto.facade.impl;

import es.in2.wallet.crypto.configuration.properties.AppProperties;
import es.in2.wallet.crypto.facade.DidServiceFacade;
import es.in2.wallet.crypto.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static es.in2.wallet.crypto.utils.Utils.PROCESS_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DidServiceFacadeImpl implements DidServiceFacade {

    private final AppProperties appProperties;
    private final KeyGenerationService keyGenerationService;
    private final DidGenerationService didGenerationService;
    private final HashiCorpVaultStorageService hashiCorpVaultStorageService;
    private final AzureKeyVaultStorageService azureKeyVaultStorageService;
    private final DataStorageService dataStorageService;

    @Override
    public Mono<String> createDidKeyAndPersistIntoWalletDataAndVault(String token) {
        String processId = MDC.get(PROCESS_ID);
        // Key Generation
        return keyGenerationService.generateKey()
                // Key Export
                .flatMap(keyGenerationService::exportKey)
                // DID Generation
                .flatMap(keyDetails -> didGenerationService.createDidKey(keyDetails.keyId())
                        .flatMap(did -> saveSecretAndPersistDID(did, keyDetails.privateKey(), token))
                        .doOnSuccess(did -> log.info("ProcessID: {} - DID created and saved successfully: {}", processId, did))
                        .doOnError(throwable -> log.error("ProcessID: {} - Failed to create or save DID: {}", processId, throwable.getMessage()))
                );
    }

    @Override
    public Mono<String> createDidKeyJwkJcsPubAndPersistIntoWalletDataAndVault(String token) {
        String processId = MDC.get(PROCESS_ID);
        // Key Generation
        return keyGenerationService.generateKey()
                // Key Export
                .flatMap(keyGenerationService::exportKey)
                // DID Generation
                .flatMap(keyDetails -> didGenerationService.createDidKeyJwkJcsPub(keyDetails.keyId())
                        .flatMap(did -> saveSecretAndPersistDID(did, keyDetails.privateKey(), token))
                        .doOnSuccess(did -> log.info("ProcessID: {} - DID created and saved successfully: {}", processId, did))
                        .doOnError(throwable -> log.error("ProcessID: {} - Failed to create or save DID: {}", processId, throwable.getMessage()))
                );
    }

    private Mono<String> saveSecretAndPersistDID(String did, String privateKey, String token) {
        if (appProperties.secretProvider().name().equals("hashicorp")) {
            return hashiCorpVaultStorageService.saveSecret(did, privateKey)
                    // DID Persistence in Wallet Data
                    .then(dataStorageService.saveDidKey(token, did))
                    .thenReturn(did);
        } else if (appProperties.secretProvider().name().equals("azure")) {
            return azureKeyVaultStorageService.saveSecret(did, privateKey)
                    // DID Persistence in Wallet Data
                    .then(dataStorageService.saveDidKey(token, did))
                    .thenReturn(did);
        } else {
            return Mono.error(new Exception("Secret provider not supported"));
        }
    }

}
