package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.entity.UserAccount;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.AuthService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserAccountRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserAccountRepository repo, PasswordEncoder encoder, AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void register(RegisterRequestDto req) {
        if (repo.existsByEmail(req.getEmail())) throw new BadRequestException("Email exists");
        UserAccount u = new UserAccount(req.getEmail(), encoder.encode(req.getPassword()), req.getRole());
        repo.save(u);
    }

    @Override
    public AuthResponseDto login(AuthRequestDto req) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        String token = jwtUtil.generateToken(auth);
        return new AuthResponseDto(token);
    }
}