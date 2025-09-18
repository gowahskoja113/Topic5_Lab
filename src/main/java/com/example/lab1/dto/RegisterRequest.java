package com.example.lab1.dto;

import com.example.lab1.entity.Role;

public record RegisterRequest(
        String username,
        String password,
        Role role
) {
}
