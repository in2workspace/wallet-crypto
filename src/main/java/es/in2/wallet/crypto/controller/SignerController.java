package es.in2.wallet.crypto.controller;

import es.in2.wallet.crypto.domain.SignRequest;
import es.in2.wallet.crypto.exception.SecretNotFoundException;
import es.in2.wallet.crypto.facade.SignerServiceFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static es.in2.wallet.crypto.utils.Utils.PROCESS_ID;

@Slf4j
@RestController
@RequestMapping("/api/v2/sign")
@RequiredArgsConstructor
public class SignerController {

    private final SignerServiceFacade signerServiceFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<String> singDocument(@RequestBody SignRequest signRequest) {
        // Create a unique ID for the process
        String processId = UUID.randomUUID().toString();
        MDC.put(PROCESS_ID, processId);
        // Async Process Start
        log.debug("ProcessID: {} - sign document: {}", processId, signRequest);
        return signerServiceFacade.signDocument(signRequest.document(),signRequest.did(),signRequest.documentType())
                .onErrorResume(SecretNotFoundException.class, Mono::error)
                .doFinally(signalType -> MDC.remove(PROCESS_ID));
    }
}
