package es.in2.wallet.crypto.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.wallet.crypto.exception.ParseErrorException;
import es.in2.wallet.crypto.model.DidRequestDTO;
import es.in2.wallet.crypto.service.CustomDidKeyService;
import es.in2.wallet.crypto.util.ApplicationUtils;
import id.walt.crypto.KeyAlgorithm;
import id.walt.crypto.KeyId;
import id.walt.model.DidMethod;
import id.walt.services.did.DidKeyCreateOptions;
import id.walt.services.did.DidService;
import id.walt.services.key.KeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CustomDidKeyServiceImpl implements CustomDidKeyService {
    private final ApplicationUtils applicationUtils;
    private final String walletDataUrl;
    @Autowired
    public CustomDidKeyServiceImpl(ApplicationUtils applicationUtils, @Value("${wallet-data.url}") String walletDataUrl) {
        this.applicationUtils = applicationUtils;
        this.walletDataUrl = walletDataUrl;
    }
    @Override
    public Mono<Void> createDidKey(String userToken) {
        KeyId keyId = KeyService.Companion.getService().generate(KeyAlgorithm.ECDSA_Secp256k1);
        String did = DidService.INSTANCE.create(DidMethod.key, keyId.getId(), null);

        return Mono.just(did)
                .flatMap(didResult -> {
                    try {
                        List<Map.Entry<String, String>> headers = new ArrayList<>();
                        headers.add(new AbstractMap.SimpleEntry<>(HttpHeaders.AUTHORIZATION, "Bearer " + userToken));
                        headers.add(new AbstractMap.SimpleEntry<>(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
                        DidRequestDTO didRequestDTO = new DidRequestDTO();
                        didRequestDTO.setDid(didResult);
                        didRequestDTO.setDidType(DidMethod.key.toString());

                        String body = new ObjectMapper().writeValueAsString(didRequestDTO);

                        return applicationUtils.postRequest(walletDataUrl, headers, body)
                                .then()
                                .doOnSuccess(result -> log.info("DID persisted successfully"))
                                .doOnError(throwable -> log.error("Error: {}", throwable.getMessage()));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new ParseErrorException("Error converting DID request to JSON"));
                    }
                })
                .doOnSuccess(result -> log.info("Success: DID persisted"))
                .doOnError(throwable -> log.error("Error: {}", throwable.getMessage()));
    }




    @Override
    public Mono<String> createDidKeyJwkJcsPub() {
        KeyId keyId = KeyService.Companion.getService().generate(KeyAlgorithm.ECDSA_Secp256r1);
        return Mono.just(DidService.INSTANCE.create(DidMethod.key, keyId.getId(), new DidKeyCreateOptions(true)))
                .doOnSuccess(result -> log.info("Success: {}", result))
                .doOnError(throwable -> log.error("Error: {}", throwable.getMessage()));
    }
}
