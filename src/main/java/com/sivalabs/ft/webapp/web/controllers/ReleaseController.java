package com.sivalabs.ft.webapp.web.controllers;

import com.sivalabs.ft.webapp.clients.features.*;
import com.sivalabs.ft.webapp.services.SecurityHelper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products/{productCode}")
public class ReleaseController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ReleaseController.class);
    private final FeatureServiceClient featureServiceClient;

    public ReleaseController(FeatureServiceClient featureServiceClient, SecurityHelper securityHelper) {
        super(securityHelper);
        this.featureServiceClient = featureServiceClient;
    }

    @GetMapping("/releases/{releaseCode}")
    public String showReleaseDetails(@PathVariable String productCode, @PathVariable String releaseCode, Model model) {
        Product product = featureServiceClient.getProductByCode(productCode).orElseThrow();
        model.addAttribute("product", product);
        ReleaseDto release = featureServiceClient.getReleaseByCode(releaseCode).orElseThrow();
        model.addAttribute("release", release);
        return "view_release";
    }

    @GetMapping("/releases/new")
    public String showCreateReleaseForm(@PathVariable String productCode, Model model) {
        Product product = featureServiceClient.getProductByCode(productCode).orElseThrow();
        model.addAttribute("product", product);
        model.addAttribute("release", new CreateReleasePayload(product.code(), "", ""));
        return "create_release";
    }

    @PostMapping("/releases")
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

    @GetMapping("/releases/{releaseCode}/edit")
    public String showEditReleaseForm(@PathVariable String productCode, @PathVariable String releaseCode, Model model) {
        Product product = featureServiceClient.getProductByCode(productCode).orElseThrow();
        model.addAttribute("product", product);
        ReleaseDto releaseDto =
                featureServiceClient.getReleaseByCode(releaseCode).orElseThrow();
        model.addAttribute("releaseCode", releaseCode);
        var updateRelease =
                new UpdateReleasePayload(releaseDto.status(), releaseDto.description(), releaseDto.createdAt());
        model.addAttribute("release", updateRelease);
        return "edit_release";
    }

    @PutMapping("/releases/{releaseCode}")
    public String updateRelease(
            @PathVariable String productCode,
            @PathVariable String releaseCode,
            @ModelAttribute("release") @Valid UpdateReleasePayload payload,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            Product product = featureServiceClient.getProductByCode(productCode).orElseThrow();
            model.addAttribute("product", product);
            return "edit_release";
        }
        featureServiceClient.updateRelease(getHeaders(), releaseCode, payload);
        log.info("Updated release {} for productCode:{}", releaseCode, productCode);
        return "redirect:/products/" + productCode;
    }
}
