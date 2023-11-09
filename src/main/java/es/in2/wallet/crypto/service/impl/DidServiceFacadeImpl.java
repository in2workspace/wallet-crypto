package es.in2.wallet.crypto.service.impl;

import es.in2.wallet.crypto.service.CustomDidKeyService;
import es.in2.wallet.crypto.service.DidServiceFacade;
import es.in2.wallet.crypto.service.WalletDataCommunicationService;
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

    // create a did:key and save it into the Wallet Data component.
    @Override
    public Mono<String> createDidKeyAndPersistIntoWalletData(String token) {
        // create did:key
        return customDidKeyService.createDidKey()
                // save did:key
                .flatMap(did -> walletDataCommunicationService.saveDidKey(token, did).thenReturn(did))
                .doOnSuccess(did -> log.info("DID created and saved successfully: {}", did))
                .doOnError(throwable -> log.error("Failed to create or save DID: {}", throwable.getMessage()));
    }

}
