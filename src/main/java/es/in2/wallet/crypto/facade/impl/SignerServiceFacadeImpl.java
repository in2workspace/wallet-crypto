package es.in2.wallet.crypto.facade.impl;

import com.fasterxml.jackson.databind.JsonNode;
import es.in2.wallet.crypto.facade.SignerServiceFacade;
import es.in2.wallet.crypto.service.HashiCorpVaultStorageService;
import es.in2.wallet.crypto.service.SignerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static es.in2.wallet.crypto.utils.Utils.PROCESS_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignerServiceFacadeImpl implements SignerServiceFacade {
    private final HashiCorpVaultStorageService hashiCorpVaultStorageService;
    private final SignerService signerService;
    @Override
    public Mono<String> signDocument(JsonNode document, String did) {
        String processId = MDC.get(PROCESS_ID);
        return hashiCorpVaultStorageService.getSecretByKey(did)
                .flatMap(privateKey ->signerService.signDocumentWithPrivateKey(document,did,privateKey))
                .doOnSuccess(signedDocument -> log.info("ProcessID: {} - Document signed: {}", signedDocument, did))
                .doOnError(throwable -> log.error("ProcessID: {} - Failed to create or save DID: {}", processId, throwable.getMessage()));
    }
}
