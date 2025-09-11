package com.example.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface User extends JpaRepository<User, Integer> {
    Optional<User>findByUsername(String username);
    boolean existsByUsername(String username);
}
