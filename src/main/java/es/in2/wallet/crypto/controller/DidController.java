package es.in2.wallet.crypto.controller;

import es.in2.wallet.crypto.service.CustomDidKeyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/dids")
@Tag(name = "DID Controller", description = "Endpoints for creating and managing DIDs")
@RequiredArgsConstructor
public class DidController {

    private final CustomDidKeyService customDidKeyService;

    @PostMapping("/key")
    @ResponseStatus(HttpStatus.CREATED)
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
    public Mono<String> createDidKey() {
        return Objects.requireNonNull(customDidKeyService.createDidKey());
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
