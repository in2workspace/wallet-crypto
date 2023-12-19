package es.in2.wallet.crypto.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.wallet.crypto.service.impl.SignerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SignerServiceImplTest {
    @Mock
    private ObjectMapper mockedObjectMapper;
    @InjectMocks
    private SignerServiceImpl signerService;

    @Test
    void testSignDocument() throws JsonProcessingException{
        String json = "{\"document\":\"sign this document\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        String privateKey = "{\"kty\":\"EC\",\"d\":\"MDtaBGOjN0SY0NtX2hFvv4uJNLrUGUWHvquqNZHwi5s\",\"use\":\"sig\",\"crv\":\"P-256\",\"kid\":\"75bb28ac9f4247248c73348f890e050c\",\"x\":\"E9pfJi7I29gtdofnJJBvC_DK3KH1eTialAMOoX6CfZw\",\"y\":\"hDfdnEyabkB-9Hf1PFYaYomSdYVwJ0NSM5CzxhOUIr0\",\"alg\":\"ES256\"}";
        String did = "did:example";
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("someKey", "someValue");


        when(mockedObjectMapper.convertValue(any(JsonNode.class), any(TypeReference.class))).thenReturn(claimsMap);
        StepVerifier.create(signerService.signDocumentWithPrivateKey(jsonNode, did, "proof", privateKey))
                .assertNext(signedDocument -> {
                    assert signedDocument != null;
                })
                .verifyComplete();
    }

}