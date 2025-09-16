package com.example.lab1.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        long id,
        String username,
        String role,
        String token
) {}

