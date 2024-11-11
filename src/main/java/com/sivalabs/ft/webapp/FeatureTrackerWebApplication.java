package com.sivalabs.ft.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FeatureTrackerWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeatureTrackerWebApplication.class, args);
    }
}
