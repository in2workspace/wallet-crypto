package es.in2.wallet.crypto.controller;

import es.in2.wallet.crypto.facade.DidServiceFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static es.in2.wallet.crypto.utils.Utils.BEARER_PREFIX;
import static es.in2.wallet.crypto.utils.Utils.PROCESS_ID;

@Tag(name = "DID Controller", description = "Endpoints for creating and managing DIDs")
@Slf4j
@RestController
@RequestMapping("/api/v2/dids")
@RequiredArgsConstructor
public class DidController {

    private final DidServiceFacade didServiceFacade;

    @PostMapping(path = "/key")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Create DID Key",
            description = "Create a DID Key."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Success",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(mediaType = "application/json")
            )
    })
    public Mono<String> createDidKey(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        // Create a unique ID for the process
        String processId = UUID.randomUUID().toString();
        MDC.put(PROCESS_ID, processId);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            // Async Process Start
            log.info("ProcessID: {} - Creating did:key...", processId);
            String token = authorizationHeader.replace(BEARER_PREFIX, "");
            return didServiceFacade.createDidKeyAndPersistIntoWalletDataAndVault(token)
                    .doFinally(signalType -> MDC.remove(PROCESS_ID));
        } else {
            log.error("ProcessID: {} - Invalid Authorization header", processId);
            return Mono.error(new IllegalArgumentException("Invalid Authorization header"));
        }
    }


    @PostMapping("/key/jwk-jcs-pub")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create did:key:jwk_jcs-pub identifier",
            description = "Generate a did:key:jwk_jcs-pub identifier using the ES256 key algorithm."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(mediaType = "application/json")
            )
    })
    public Mono<String> createDidKeyJwkJcsPub(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        // Create a unique ID for the process
        String processId = UUID.randomUUID().toString();
        MDC.put(PROCESS_ID, processId);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            // Async Process Start
            log.debug("ProcessID: {} - Create did:key:jwk-jcs-pub", processId);
            String token = authorizationHeader.replace(BEARER_PREFIX, "");
            return didServiceFacade.createDidKeyJwkJcsPubAndPersistIntoWalletDataAndVault(token);
        } else {
            log.error("ProcessID: {} - Invalid Authorization header", processId);
            return Mono.error(new IllegalArgumentException("Invalid Authorization header"));
        }
    }

}
