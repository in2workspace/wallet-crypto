package es.in2.wallet.crypto.controller;

import es.in2.wallet.crypto.service.VaultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/credentials")
@RequiredArgsConstructor
public class CredentialController {

    private final VaultService vaultService;

    @PostMapping
    public void addCredential() {
        vaultService.addCredential();
    }

    @GetMapping
    public void getCredential() {
        log.info("getCredential()");
        vaultService.getCredential();
    }


}
