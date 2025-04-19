package com.sivalabs.ft.webapp.clients.users;

public record SyncUserCommand(String uuid, String email, String fullName, String role) {}
