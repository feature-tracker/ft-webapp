package com.sivalabs.ft.webapp;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ft")
public record ApplicationProperties(String apiGatewayUrl) {}
