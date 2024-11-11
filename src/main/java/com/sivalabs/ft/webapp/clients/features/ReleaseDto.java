package com.sivalabs.ft.webapp.clients.features;

import java.io.Serializable;
import java.time.Instant;

public record ReleaseDto(
        Long id,
        String code,
        String description,
        String status,
        String createdBy,
        Instant createdAt,
        String updatedBy,
        Instant updatedAt)
        implements Serializable {}
