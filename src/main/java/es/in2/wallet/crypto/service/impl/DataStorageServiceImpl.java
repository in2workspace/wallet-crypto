package es.in2.wallet.crypto.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.wallet.crypto.config.properties.WalletDataProperties;
import es.in2.wallet.crypto.exception.ParseErrorException;
import es.in2.wallet.crypto.domain.DidRequestDTO;
import es.in2.wallet.crypto.service.DataStorageService;
import id.walt.model.DidMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static es.in2.wallet.crypto.util.Utils.postRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataStorageServiceImpl implements DataStorageService {

    private final ObjectMapper objectMapper;
    private final WalletDataProperties walletDataProperties;

    @Override
    public Mono<Void> saveDidKey(String token, String did) {
        List<Map.Entry<String, String>> headers = new ArrayList<>();
        headers.add(new AbstractMap.SimpleEntry<>(HttpHeaders.AUTHORIZATION, "Bearer " + token));
        headers.add(new AbstractMap.SimpleEntry<>(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
        DidRequestDTO didRequestDTO = DidRequestDTO.builder()
                .did(did)
                .didType(DidMethod.key.toString())
                .build();
        try {
            String body = objectMapper.writeValueAsString(didRequestDTO);
            return postRequest(walletDataProperties.url(), headers, body)
                    .then()
                    .doOnSuccess(result -> log.info("DID persisted successfully"))
                    .doOnError(throwable -> log.error("Error: {}", throwable.getMessage()));
        } catch (JsonProcessingException e) {
            return Mono.error(new ParseErrorException("Error converting DID request to JSON"));
        }
    }

}
