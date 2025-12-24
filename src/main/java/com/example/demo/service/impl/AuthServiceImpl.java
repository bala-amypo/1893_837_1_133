package com.example.demo.service.impl;

import com.example.demo.dto.AuthRequestDto;
import com.example.demo.dto.AuthResponseDto;
import com.example.demo.dto.RegisterRequestDto;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserAccountRepository userRepo,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void register(RegisterRequestDto dto) {
        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }

        UserAccount user = new UserAccount();
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        userRepo.save(user);
    }

    @Override
    public AuthResponseDto login(AuthRequestDto dto) {
        UserAccount user = userRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponseDto(token, Instant.now().plusMillis(jwtUtil.getExpirationMillis()));
    }
}
