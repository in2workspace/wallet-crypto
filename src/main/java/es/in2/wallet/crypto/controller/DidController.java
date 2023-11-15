package es.in2.wallet.crypto.controller;

import es.in2.wallet.crypto.service.CustomDidKeyService;
import es.in2.wallet.crypto.service.DidServiceFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/dids")
@Tag(name = "DID Controller", description = "Endpoints for creating and managing DIDs")
@RequiredArgsConstructor
public class DidController {

    private final DidServiceFacade didServiceFacade;
    private final CustomDidKeyService customDidKeyService;

    @PostMapping("/key")
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
    public Mono<String> createDidKey(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return didServiceFacade.createDidKeyAndPersistIntoWalletDataAndVault(token);
        } else {
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
    public Mono<String> createDidKeyJwkJcsPub() {
        return Objects.requireNonNull(customDidKeyService.createDidKeyJwkJcsPub());
    }

}
