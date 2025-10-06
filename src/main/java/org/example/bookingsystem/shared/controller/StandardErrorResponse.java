package org.example.bookingsystem.shared.controller;

import lombok.Builder;

import java.time.Instant;

@Builder
public record StandardErrorResponse(
        Instant timestamp,
        int status,
        String message) {
}