package com.sivalabs.ft.webapp.web.controllers;

import com.sivalabs.ft.webapp.clients.features.CreateReleasePayload;
import com.sivalabs.ft.webapp.clients.features.FeatureServiceClient;
import com.sivalabs.ft.webapp.clients.features.Product;
import com.sivalabs.ft.webapp.clients.features.ReleaseDto;
import com.sivalabs.ft.webapp.services.SecurityHelper;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    private final FeatureServiceClient featureServiceClient;
    private final SecurityHelper securityHelper;

    public HomeController(FeatureServiceClient featureServiceClient, SecurityHelper securityHelper) {
        this.featureServiceClient = featureServiceClient;
        this.securityHelper = securityHelper;
    }

    @GetMapping("")
    public String home(Model model, @AuthenticationPrincipal OAuth2User principal) {
        Object username = principal == null ? "Guest" : principal.getAttribute("name");
        model.addAttribute("username", username);
        List<Product> products = featureServiceClient.getProducts();
        model.addAttribute("products", products);
        return "home";
    }

    @GetMapping("/products/{code}")
    public String viewProductDetails(@PathVariable String code, Model model) {
        log.info("Show product details for code:{}", code);
        Product product = featureServiceClient.getProductByCode(code).orElseThrow();
        List<ReleaseDto> releases = featureServiceClient.getProductReleases(code);
        model.addAttribute("product", product);
        model.addAttribute("releases", releases);
        return "product_details";
    }

    @GetMapping("/products/{code}/releases/new")
    public String showCreateReleaseForm(@PathVariable String code, Model model) {
        Product product = featureServiceClient.getProductByCode(code).orElseThrow();
        model.addAttribute("product", product);
        model.addAttribute("release", new CreateReleasePayload(product.code(), "", ""));
        return "create_release";
    }

    @PostMapping("/products/{productCode}/releases")
    public String createRelease(
            @PathVariable String productCode,
            @ModelAttribute("release") @Valid CreateReleasePayload payload,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            Product product = featureServiceClient.getProductByCode(productCode).orElseThrow();
            model.addAttribute("product", product);
            return "create_release";
        }
        featureServiceClient.createRelease(getHeaders(), payload);
        log.info("Created new release {} for productCode:{}", payload.code(), productCode);
        return "redirect:/products/" + productCode;
    }

    private MultiValueMap<String, String> getHeaders() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        String accessToken = securityHelper.getAccessToken();
        headers.add("Authorization", "Bearer " + accessToken);
        return headers;
    }
}
