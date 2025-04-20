package com.sivalabs.ft.webapp.web.controllers;

import com.sivalabs.ft.webapp.clients.features.FeatureDto;
import com.sivalabs.ft.webapp.clients.features.FeatureServiceClient;
import com.sivalabs.ft.webapp.clients.features.Product;
import com.sivalabs.ft.webapp.services.SecurityHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products/{productCode}")
public class FeatureController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(FeatureController.class);
    private final FeatureServiceClient featureServiceClient;

    FeatureController(FeatureServiceClient featureServiceClient, SecurityHelper securityHelper) {
        super(securityHelper);
        this.featureServiceClient = featureServiceClient;
    }

    @GetMapping("/features/{featureCode}")
    public String showReleaseDetails(@PathVariable String productCode, @PathVariable String featureCode, Model model) {
        Product product = featureServiceClient.getProductByCode(productCode).orElseThrow();
        model.addAttribute("product", product);
        FeatureDto feature = featureServiceClient.getFeatureByCode(featureCode).orElseThrow();
        model.addAttribute("feature", feature);
        return "view_feature";
    }
}
