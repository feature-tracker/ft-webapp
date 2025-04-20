package com.sivalabs.ft.webapp.web.controllers;

import com.sivalabs.ft.webapp.services.SecurityHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;

abstract class BaseController {
    protected final SecurityHelper securityHelper;

    BaseController(SecurityHelper securityHelper) {
        this.securityHelper = securityHelper;
    }

    @ModelAttribute("currentUserName")
    public String currentUserName(@AuthenticationPrincipal OAuth2User principal) {
        Object username = principal == null ? "Guest" : principal.getAttribute("name");
        return String.valueOf(username);
    }

    protected MultiValueMap<String, String> getHeaders() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        String accessToken = securityHelper.getAccessToken();
        headers.add("Authorization", "Bearer " + accessToken);
        return headers;
    }
}
