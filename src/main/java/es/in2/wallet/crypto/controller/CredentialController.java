package es.in2.wallet.crypto.controller;

import es.in2.wallet.crypto.service.VaultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/credentials")
@RequiredArgsConstructor
public class CredentialController {

    private final VaultService vaultService;
    @GetMapping
    public Mono<ResponseEntity<String>> getCredential(@RequestParam String did) {
        log.info("getCredential() for DID: {}", did);
        return vaultService.getSecret(did)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
