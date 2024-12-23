package com.sivalabs.ft.webapp.clients.features;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestClient;

@Component
public class FeatureServiceClient {
    private static final Logger log = LoggerFactory.getLogger(FeatureServiceClient.class);
    private final RestClient restClient;

    public FeatureServiceClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public List<Product> getProducts() {
        return this.restClient
                .get()
                .uri("/features/api/products")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public Optional<Product> getProductByCode(String code) {
        Product product = this.restClient
                .get()
                .uri("/features/api/products/{code}", code)
                .retrieve()
                .body(Product.class);
        return Optional.ofNullable(product);
    }

    public List<ReleaseDto> getProductReleases(String productCode) {
        return this.restClient
                .get()
                .uri("/features/api/releases?productCode={productCode}", productCode)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public void createRelease(
            @RequestHeader MultiValueMap<String, String> headers, @RequestBody @Valid CreateReleasePayload payload) {
        ResponseEntity<Void> response = this.restClient
                .post()
                .uri("/features/api/releases")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(h -> h.addAll(headers))
                .body(payload)
                .retrieve()
                .toBodilessEntity();
        if (response.getStatusCode().isError()) {
            log.error("Failed to create new release. Status: {}", response.getStatusCode());
            throw new RuntimeException("Failed to create new release");
        }
    }

    public void createFeature(
            @RequestHeader MultiValueMap<String, String> headers, @RequestBody @Valid CreateFeaturePayload payload) {
        ResponseEntity<Void> response = this.restClient
                .post()
                .uri("/features/api/features")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(h -> h.addAll(headers))
                .body(payload)
                .retrieve()
                .toBodilessEntity();
        if (response.getStatusCode().isError()) {
            log.error("Failed to create new feature. Status: {}", response.getStatusCode());
            throw new RuntimeException("Failed to create new feature");
        }
    }
}
