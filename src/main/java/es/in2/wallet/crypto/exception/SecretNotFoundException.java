package es.in2.wallet.crypto.exception;

public class SecretNotFoundException extends RuntimeException{
    public SecretNotFoundException(String message) {
        super(message);
    }
}
