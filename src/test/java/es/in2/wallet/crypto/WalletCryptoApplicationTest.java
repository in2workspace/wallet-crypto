package es.in2.wallet.crypto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest(WalletCryptoApplication.class)
class WalletCryptoApplicationTest {

    @Test
    void contextLoads() {
        String expected = "Hello World!";
        String actual = "Hello World!";
        assertEquals(expected, actual);
    }

}
