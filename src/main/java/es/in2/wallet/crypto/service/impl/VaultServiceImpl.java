package es.in2.wallet.crypto.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.wallet.crypto.model.KeySecret;
import es.in2.wallet.crypto.service.VaultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.VaultResponseSupport;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
@Slf4j
public class VaultServiceImpl implements VaultService {
    private final VaultOperations vaultOperations;

    @Override
    public Mono<Void> saveSecret(String did, String privateKeyString) {
        return convertStringToSecret(privateKeyString)
                .flatMap(keySecret -> Mono.fromCallable(() -> {
                    vaultOperations.write("kv/" + did, keySecret);
                    return null;
                }).subscribeOn(Schedulers.boundedElastic()))
                .then();
    }

    @Override
    public Mono<String> getSecret(String did) {
        return Mono.fromCallable(() -> {
            VaultResponseSupport<Object> response = vaultOperations.read("kv/" + did, Object.class);
            if (response != null && response.getData() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(response.getData());
                JsonNode jsonNode = objectMapper.readTree(json);
                if (jsonNode.has("privateKey")) {
                    return jsonNode.get("privateKey").asText();
                } else {
                    log.debug("Private key not found in the JSON response for DID: {}", did);
                    return null;
                }
            } else {
                log.debug("No secret found for DID: {}", did);
                return null;
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> deleteSecret(String did) {
        return Mono.fromCallable(() -> {
            vaultOperations.delete("kv/" + did);
            return null;
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }
    private Mono<KeySecret> convertStringToSecret(String privateKeyString) {
        return Mono.just(KeySecret.builder().privateKey(privateKeyString).build());
    }
}
