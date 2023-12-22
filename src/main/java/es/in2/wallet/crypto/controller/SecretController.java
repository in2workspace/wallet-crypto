package es.in2.wallet.crypto.controller;

import es.in2.wallet.crypto.exception.SecretNotFoundException;
import es.in2.wallet.crypto.facade.SecretServiceFacade;
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
@RequestMapping("/api/v2/secrets")
@RequiredArgsConstructor
public class SecretController {

    private final SecretServiceFacade secretServiceFacade;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> getSecretByDID(@RequestParam String did) {
        // Create a unique ID for the process
        String processId = UUID.randomUUID().toString();
        MDC.put(PROCESS_ID, processId);
        // Async Process Start
        log.debug("ProcessID: {} - Get Secret by DID: {}", processId, did);
        return secretServiceFacade.getSecretByDID(did)
                .onErrorResume(SecretNotFoundException.class, Mono::error)
                .doFinally(signalType -> MDC.remove(PROCESS_ID));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteSecretByDID(@RequestParam String did) {
        // Create a unique ID for the process
        String processId = UUID.randomUUID().toString();
        MDC.put(PROCESS_ID, processId);
        // Async Process Start
        log.debug("ProcessID: {} - Delete Secret by DID: {}", processId, did);
        return secretServiceFacade.deleteSecretByDID(did)
                .onErrorResume(SecretNotFoundException.class, Mono::error)
                .doFinally(signalType -> MDC.remove(PROCESS_ID));
    }

}
