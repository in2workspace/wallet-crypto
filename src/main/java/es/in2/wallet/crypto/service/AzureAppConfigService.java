package es.in2.wallet.crypto.service;

import reactor.core.publisher.Mono;

public interface AzureAppConfigService {
    Mono<String> getConfiguration();
}
