package com.example.lab1.mapper;

import com.example.lab1.dto.AuthResponse;
import com.example.lab1.dto.RegisterRequest;
import com.example.lab1.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public User toEntity(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(request.password()); // nhớ encode khi lưu
        user.setRole(request.role());
        return user;
    }

    public AuthResponse toAuthResponse(User user, String token) {
        return AuthResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole().name())
                .token(token)
                .build();
    }

}