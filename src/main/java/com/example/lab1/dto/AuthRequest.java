package com.example.lab1.dto;

import com.example.lab1.entity.Role;

public record AuthRequest(
        String username,
        String password
) {}
