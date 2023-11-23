package es.in2.wallet.crypto.service.impl;

import com.azure.data.appconfiguration.ConfigurationClient;
import es.in2.wallet.crypto.service.AzureAppConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static es.in2.wallet.crypto.util.Utils.PROCESS_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AzureAppConfigServiceImpl implements AzureAppConfigService {

    private static final String AZURE_CONFIG_PREFIX = "/gencat.iep.verifier.vclogin.ms";
    private static final String AZURE_CONFIG_PATH = "/key1";
    private static final String AZURE_CONFIG_LABEL = "dev";

    private final ConfigurationClient configurationClient;

    @Override
    public Mono<String> getConfiguration() {
        String processId = MDC.get(PROCESS_ID);
        return Mono.fromCallable(() -> {
                    try {
                        return configurationClient
                                .getConfigurationSetting(AZURE_CONFIG_PREFIX + AZURE_CONFIG_PATH, AZURE_CONFIG_LABEL)
                                .getValue();
                    } catch (Exception e) {
                        return "Communication with Key Vault failed: " + e;
                    }
                })
                .doOnSuccess(voidValue -> log.info("ProcessID: {} - Secret retrieved successfully", processId))
                .onErrorResume(Exception.class, Mono::error);
    }

}
