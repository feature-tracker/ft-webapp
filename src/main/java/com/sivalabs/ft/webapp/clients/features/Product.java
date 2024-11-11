package com.sivalabs.ft.webapp.clients.features;

public record Product(
        Long id, String code, String name, String description, String imageUrl, Boolean disabled, String createdBy) {}
