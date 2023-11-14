package es.in2.wallet.crypto.exception.handler;

import es.in2.wallet.crypto.exception.ParseErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import javax.security.auth.login.CredentialNotFoundException;


@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(ParseErrorException.class)
    public Mono<ResponseEntity<Void>> parseErrorException(ParseErrorException e) {
        log.error(e.getMessage());
        return Mono.just(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler(CredentialNotFoundException.class)
    public Mono<ResponseEntity<Void>> credentialNotFoundException(CredentialNotFoundException e) {
        log.error(e.getMessage());
        return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
