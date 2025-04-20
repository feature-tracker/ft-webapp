package com.sivalabs.ft.webapp.clients.features;

import java.io.Serializable;
import java.time.Instant;

public record FeatureDto(
        Long id,
        String code,
        String title,
        String description,
        FeatureStatus status,
        String assignedTo,
        String createdBy,
        Instant createdAt,
        String updatedBy,
        Instant updatedAt)
        implements Serializable {}
