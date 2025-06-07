package com.sivalabs.ft.webapp.clients.users;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
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
}
