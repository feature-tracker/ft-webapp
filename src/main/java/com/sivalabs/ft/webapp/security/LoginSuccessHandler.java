package com.sivalabs.ft.webapp.security;

import com.sivalabs.ft.webapp.clients.users.SyncUserCommand;
import com.sivalabs.ft.webapp.clients.users.UserServiceClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserServiceClient userServiceClient;

    public LoginSuccessHandler(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
        // Where to redirect after login if no saved request:
        setDefaultTargetUrl("/");
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOidcUser oidcUser = (DefaultOidcUser) token.getPrincipal();
        Map<String, Object> claims = oidcUser.getIdToken().getClaims();

        String keycloakId = claims.get("sub").toString();
        String email = claims.get("email").toString();
        String fullName = claims.get("name").toString();
        userServiceClient.syncUser(new SyncUserCommand(keycloakId, email, fullName, "ROLE_USER"));

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
