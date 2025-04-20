package com.sivalabs.ft.webapp.web.controllers;

import com.sivalabs.ft.webapp.clients.features.FeatureDto;
import com.sivalabs.ft.webapp.clients.features.FeatureServiceClient;
import com.sivalabs.ft.webapp.clients.features.Product;
import com.sivalabs.ft.webapp.clients.features.ReleaseDto;
import com.sivalabs.ft.webapp.services.SecurityHelper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final FeatureServiceClient featureServiceClient;

    public ProductController(FeatureServiceClient featureServiceClient, SecurityHelper securityHelper) {
        super(securityHelper);
        this.featureServiceClient = featureServiceClient;
    }

    @GetMapping("")
    public String home(Model model) {
        List<Product> products = featureServiceClient.getProducts();
        model.addAttribute("products", products);
        return "home";
    }

    @GetMapping("/products/{code}")
    public String showProductDetails(@PathVariable String code, Model model) {
        log.info("Show product details for code:{}", code);
        Product product = featureServiceClient.getProductByCode(code).orElseThrow();
        List<ReleaseDto> releases = featureServiceClient.getProductReleases(code);
        List<FeatureDto> features = featureServiceClient.getProductFeatures(code);
        model.addAttribute("product", product);
        model.addAttribute("releases", releases);
        model.addAttribute("features", features);
        return "view_product";
    }
}
