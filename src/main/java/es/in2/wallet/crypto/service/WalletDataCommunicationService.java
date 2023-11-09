package es.in2.wallet.crypto.service;

import reactor.core.publisher.Mono;

public interface WalletDataCommunicationService {
    Mono<Void> saveDidKey(String token, String did);
}
