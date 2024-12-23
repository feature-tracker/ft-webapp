package com.sivalabs.ft.webapp.clients.features;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateFeaturePayload(
        @NotEmpty(message = "Product code is required") String productCode,
        @NotEmpty(message = "Release code is required") String releaseCode,
        @NotEmpty(message = "Code is required") @Size(max = 50, message = "Code cannot exceed 50 characters")
                String code,
        @NotEmpty(message = "Title is required") @Size(max = 500, message = "Title cannot exceed 500 characters")
                String title,
        String description,
        String assignedTo) {}
