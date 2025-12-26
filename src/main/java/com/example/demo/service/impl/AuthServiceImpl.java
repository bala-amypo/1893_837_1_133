package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.entity.UserAccount;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserAccountRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserAccountRepository userRepo, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void register(RegisterRequestDto dto) {
        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }
        UserAccount u = new UserAccount();
        u.setEmail(dto.getEmail());
        u.setFullName(dto.getFullName());
        u.setPassword(encoder.encode(dto.getPassword()));
        u.setRole(dto.getRole() == null ? "ROLE_USER" : dto.getRole());
        u.prePersist();
        userRepo.save(u);
    }

    @Override
    public AuthResponseDto login(AuthRequestDto dto) {
        UserAccount u = userRepo.findByEmail(dto.getEmail()).orElseThrow(() -> new BadRequestException("Invalid credentials"));
        if (!encoder.matches(dto.getPassword(), u.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(u);
        return new AuthResponseDto(token, jwtUtil.getExpiry(token));
    }
}
