package com.example.lab1.service;

import com.example.lab1.dto.AuthRequest;
import com.example.lab1.dto.AuthResponse;
import com.example.lab1.dto.RegisterRequest;
import com.example.lab1.dto.RegisterResponse;
import com.example.lab1.entity.Role;
import com.example.lab1.entity.User;
import com.example.lab1.exception.InvalidCredentialsException;
import com.example.lab1.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username()))
            throw new RuntimeException("Username already exists");

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(BCrypt.hashpw(request.password(), BCrypt.gensalt()));
        user.setRole(request.role() == null ? Role.USER : request.role());

        User savedUser = userRepository.save(user);

        return new RegisterResponse(
                savedUser.getUsername(),
                savedUser.getRole().toString()
        );
    }

    public AuthResponse login(AuthRequest request) {
        var user = userRepository.findByUsername(request.username())
                .orElseThrow();
        // Tạo JWT token
        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }

}
