package com.example.demo.service;
import com.example.demo.dto.*;

public interface AuthService {
    void register(RegisterRequestDto request);
    AuthResponseDto login(AuthRequestDto request);
}