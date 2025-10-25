package com.davsilvam.pokedecks.config.errors;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(
        HttpStatus status,
        String message,
        LocalDateTime timestamp
) {
}
