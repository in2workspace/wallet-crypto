package es.in2.wallet.crypto.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import es.in2.wallet.crypto.domain.DocumentType;
import es.in2.wallet.crypto.exception.ParseErrorException;
import es.in2.wallet.crypto.service.SignerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

import static es.in2.wallet.crypto.domain.DocumentType.*;
import static es.in2.wallet.crypto.utils.Utils.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class SignerServiceImpl implements SignerService {
    private final ObjectMapper objectMapper;
    @Override
    public Mono<String> signDocumentWithPrivateKey(JsonNode document, String did, String documentType, String privateKey) {
        String processId = MDC.get(PROCESS_ID);

        return identifyDocumentType(documentType)
                .flatMap(docType -> Mono.fromCallable(() -> {
                    try {
                        ECKey ecJWK = JWK.parse(privateKey).toECKey();
                        log.debug("ECKey: {}", ecJWK);

                        JWSAlgorithm jwsAlgorithm = mapToJWSAlgorithm(ecJWK.getAlgorithm());
                        JWSSigner signer = new ECDSASigner(ecJWK);

                        JOSEObjectType joseObjectType = docType.equals(PROOF_DOCUMENT) ?
                                new JOSEObjectType("openid4vci-proof+jwt") : JOSEObjectType.JWT;

                        JWSHeader header = new JWSHeader.Builder(jwsAlgorithm)
                                .type(joseObjectType)
                                .keyID(did)
                                .build();

                        JWTClaimsSet payload = convertJsonNodeToJWTClaimsSet(document);
                        SignedJWT signedJWT = new SignedJWT(header, payload);
                        signedJWT.sign(signer);
                        log.debug("JWT signed successfully");
                        return signedJWT.serialize();
                    } catch (Exception e) {
                        log.error("Error while creating the Signed JWT", e);
                        throw new ParseErrorException("Error while encoding the JWT: " + e.getMessage());
                    }
                }))
                .doOnSuccess(jwt -> log.debug("ProcessID: {} - Created JWT: {}", processId, jwt))
                .doOnError(throwable -> log.error("ProcessID: {} - Error creating the jwt: {}", processId, throwable.getMessage()));
    }
    private JWSAlgorithm mapToJWSAlgorithm(Algorithm algorithm) {
        return JWSAlgorithm.parse(algorithm.getName());
    }

    private JWTClaimsSet convertJsonNodeToJWTClaimsSet(JsonNode jsonNode){
        Map<String, Object> claimsMap = objectMapper.convertValue(jsonNode, new TypeReference<>() {
        });
        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
        for (Map.Entry<String, Object> entry : claimsMap.entrySet()) {
            builder.claim(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }
    private Mono<DocumentType> identifyDocumentType(String documentType) {
        return Mono.fromSupplier(() -> {
            if (PROOF_DOCUMENT_PATTERN.matcher(documentType).matches()) {
                return PROOF_DOCUMENT;
            } else if (VC_DOCUMENT_PATTERN.matcher(documentType).matches()) {
                return VC_DOCUMENT;
            } else if (VP_DOCUMENT_PATTERN.matcher(documentType).matches()) {
                return VP_DOCUMENT;
            } else {
                log.warn("Unknown document type: {}", documentType);
                return UNKNOWN;
            }
        });
    }

}
