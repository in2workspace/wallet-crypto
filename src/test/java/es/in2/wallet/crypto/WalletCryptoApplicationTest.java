package es.in2.wallet.crypto;

import es.in2.wallet.crypto.controller.DidController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
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
