package es.in2.wallet.crypto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class WalletCryptoApplicationTest {

    @Test
    void contextLoads() {
        String expected = "Hello World!";
        String actual = "Hello World!";
        assertEquals(expected, actual);
    }

	
}
