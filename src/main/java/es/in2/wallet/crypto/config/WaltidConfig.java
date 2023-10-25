package es.in2.wallet.crypto.config;

import id.walt.servicematrix.ServiceMatrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WaltidConfig {

    @Bean
    public void instanceServiceMatrix() {
        new ServiceMatrix("service-matrix.properties");
    }

}
