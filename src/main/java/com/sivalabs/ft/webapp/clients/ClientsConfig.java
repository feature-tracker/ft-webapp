package com.sivalabs.ft.webapp.clients;

import com.sivalabs.ft.webapp.ApplicationProperties;
import java.time.Duration;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;

@Configuration
class ClientsConfig {
    private final ApplicationProperties properties;

    ClientsConfig(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Bean
    RestClientCustomizer restClientCustomizer() {
        JdkClientHttpRequestFactory requestFactory = ClientHttpRequestFactoryBuilder.jdk()
                .build(ClientHttpRequestFactorySettings.defaults()
                        .withConnectTimeout(Duration.ofSeconds(5))
                        .withReadTimeout(Duration.ofSeconds(5)));

        return restClientBuilder ->
                restClientBuilder.baseUrl(properties.apiGatewayUrl()).requestFactory(requestFactory);
    }
}
