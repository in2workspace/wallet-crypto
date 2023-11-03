package es.in2.wallet.crypto.config;

import id.walt.servicematrix.ServiceMatrix;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WaltidConfig {

    @Tag(name = "WaltidConfig", description = "Injects Walt.id services at runtime")
    @Bean
    public void instanceServiceMatrix() {
        new ServiceMatrix("service-matrix.properties");
    }
}
