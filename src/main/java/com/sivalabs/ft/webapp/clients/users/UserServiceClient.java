package com.sivalabs.ft.webapp.clients.users;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserServiceClient {
    private static final Logger log = LoggerFactory.getLogger(UserServiceClient.class);
    private final RestClient restClient;

    public UserServiceClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public List<UserDto> getUsers() {
        return this.restClient.get().uri("/users/api/users").retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public void syncUser(SyncUserCommand cmd) {
        ResponseEntity<Void> responseEntity = this.restClient
                .put()
                .uri("/users/api/users/{uuid}", cmd.uuid())
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        """
                        {
                            "email": "%s",
                            "fullName": "%s",
                            "role": "%s"
                        }
                        """
                                .formatted(cmd.email(), cmd.fullName(), cmd.role()))
                .retrieve()
                .toBodilessEntity();
        if (responseEntity.getStatusCode().isError()) {
            log.error("Failed to sync user. Status: {}", responseEntity.getStatusCode());
            throw new RuntimeException("Failed to sync user");
        } else {
            log.info("User information is synced successfully for email:{}", cmd.email());
        }
    }
}
