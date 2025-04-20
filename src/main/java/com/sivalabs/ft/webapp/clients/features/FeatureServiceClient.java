package com.sivalabs.ft.webapp.clients.features;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
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

    public Optional<ReleaseDto> getReleaseByCode(String code) {
        ReleaseDto releaseDto = this.restClient
                .get()
                .uri("/features/api/releases/{code}", code)
                .retrieve()
                .body(ReleaseDto.class);
        return Optional.ofNullable(releaseDto);
    }

    public void createRelease(
            @RequestHeader MultiValueMap<String, String> headers, @RequestBody @Valid CreateReleasePayload payload) {
        try {
            this.restClient
                    .post()
                    .uri("/features/api/releases")
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(h -> h.addAll(headers))
                    .body(payload)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            log.error("Failed to create new release", e);
            throw new RuntimeException("Failed to create new release");
        }
    }

    public void updateRelease(
            MultiValueMap<String, String> headers,
            String releaseCode,
            @RequestBody @Valid UpdateReleasePayload payload) {
        try {
            this.restClient
                    .put()
                    .uri("/features/api/releases/{releaseCode}", releaseCode)
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(h -> h.addAll(headers))
                    .body(payload)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            log.error("Failed to update release", e);
            throw new RuntimeException("Failed to update release");
        }
    }

    public List<FeatureDto> getProductFeatures(String productCode) {
        return this.restClient
                .get()
                .uri("/features/api/features?productCode={productCode}", productCode)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public void createFeature(
            @RequestHeader MultiValueMap<String, String> headers, @RequestBody @Valid CreateFeaturePayload payload) {
        try {
            this.restClient
                    .post()
                    .uri("/features/api/features")
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(h -> h.addAll(headers))
                    .body(payload)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            log.error("Failed to create new feature", e);
            throw new RuntimeException("Failed to create new feature");
        }
    }
}
