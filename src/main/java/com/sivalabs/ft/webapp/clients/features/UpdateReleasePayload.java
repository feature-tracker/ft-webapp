package com.sivalabs.ft.webapp.clients.features;

import java.time.Instant;

public record UpdateReleasePayload(ReleaseStatus status, String description, Instant releasedAt) {}
