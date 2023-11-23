package es.in2.wallet.crypto.config.properties;

import org.springframework.boot.context.properties.bind.ConstructorBinding;

public record OpenApiInfoLicenseProperties(String name, String url) {

    @ConstructorBinding
    public OpenApiInfoLicenseProperties(String name, String url) {
        this.name = name;
        this.url = url;
    }

}
