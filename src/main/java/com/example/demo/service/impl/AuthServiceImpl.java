package com.example.demo.service.impl;

import com.example.demo.dto.RegisterRequestDto;
import com.example.demo.entity.UserAccount;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserAccountRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserAccountRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserAccount register(RegisterRequestDto req) {
        Optional<UserAccount> existing = userRepository.findByEmail(req.getEmail());
        if (existing.isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        UserAccount user = new UserAccount();
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setFullName(req.getFullName());
        user.setRole(req.getRole());
        
        return userRepository.save(user);
    }
}