package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService a, JwtUtil j){ this.authService=a; this.jwtUtil=j; }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto dto){
        authService.register(dto);
        return ResponseEntity.ok(Map.of("status","registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto dto){
        try{
            String token = authService.login(dto);
            return ResponseEntity.ok(Map.of("token",token));
        }catch(Exception e){
            throw new BadRequestException("Invalid credentials");
        }
    }
}
