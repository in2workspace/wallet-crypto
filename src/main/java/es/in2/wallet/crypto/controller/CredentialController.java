package es.in2.wallet.crypto.controller;

import es.in2.wallet.crypto.exception.SecretNotFoundException;
import es.in2.wallet.crypto.service.VaultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/credentials")
@RequiredArgsConstructor
public class CredentialController {

    private final VaultService vaultService;
    @GetMapping
    public Mono<String> getCredential(@RequestParam String did) {
        log.info("getCredential() for DID: {}", did);
        return vaultService.getSecret(did)
                .onErrorResume(SecretNotFoundException.class, Mono::error);
    }

    @DeleteMapping
    public Mono<Void> deleteCredential(@RequestParam String did) {
        log.info("deleteCredential() for DID: {}", did);
        return vaultService.deleteSecret(did)
                .onErrorResume(SecretNotFoundException.class, Mono::error);
    }



}
