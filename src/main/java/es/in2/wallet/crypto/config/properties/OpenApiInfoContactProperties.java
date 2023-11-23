package es.in2.wallet.crypto.config.properties;

import org.springframework.boot.context.properties.bind.ConstructorBinding;

public record OpenApiInfoContactProperties(String email, String name, String url) {

    @ConstructorBinding
    public OpenApiInfoContactProperties(String email, String name, String url) {
        this.email = email;
        this.name = name;
        this.url = url;
    }

}
