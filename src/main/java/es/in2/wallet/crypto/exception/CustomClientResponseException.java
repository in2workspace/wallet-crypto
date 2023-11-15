package es.in2.wallet.crypto.exception;

import lombok.Getter;

@Getter
public class CustomClientResponseException extends RuntimeException {
    private final int statusCode;
    private final String responseBody;

    public CustomClientResponseException(int statusCode, String responseBody) {
        super("ClientResponse has a status code " + statusCode + " with body " + responseBody);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

}