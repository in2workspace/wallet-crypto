package es.in2.wallet.crypto.service.impl;

import es.in2.wallet.crypto.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class DidServiceFacadeImpl implements DidServiceFacade {

    private final CustomDidKeyService customDidKeyService;
    private final WalletDataCommunicationService walletDataCommunicationService;
    private final VaultService vaultService;
    private final CustomKeyService customKeyService;

    // create a did:key and save it into the Wallet Data component.
    @Override
    public Mono<String> createDidKeyAndPersistIntoWalletDataAndVault(String token) {
        // Step 1: Generate KeyId using CustomKeyService
        return customKeyService.createKeyIdAndExportPrivateKey()
                // Step 2: Create DID using KeyId from CustomDidKeyService
                .flatMap(keyDetails -> customDidKeyService.createDidKey(keyDetails.getKeyId())
                        // Step 3: Save did and KeyId in VaultService
                        .flatMap(did -> vaultService.saveSecret(did, keyDetails.getPrivateKey())
                                // Step 4: Call WalletDataCommunicationService to persist the did
                                .then(walletDataCommunicationService.saveDidKey(token, did))
                                .thenReturn(did)))
                .doOnSuccess(did -> log.info("DID created and saved successfully: {}", did))
                .doOnError(throwable -> log.error("Failed to create or save DID: {}", throwable.getMessage()));
    }

}
