package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserRepository userRepo;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, UserService userService, UserRepository userRepo) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> body) {
        try {
            // Call service to register user
            User user = userService.registerUser(body);

            // FIX: Manually build a JSON-safe response map.
            // Returning the 'User' entity directly causes Serialization Errors (status 500)
            // because of recursive fields or LocalDateTime issues.
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("id", user.getId());
            response.put("email", user.getEmail());
            response.put("name", user.getName());
            response.put("message", "User registered successfully");
            
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Log error to console so you can see it in TestNG output
            System.err.println("REGISTRATION FAILED: " + e.getMessage());
            e.printStackTrace();
            
            // Return 400 Bad Request with the error message
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
            
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepo.findByEmail(req.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found after auth"));
            
            Set<String> roles = user.getRoles().stream()
                    .map(r -> r.getName())
                    .collect(Collectors.toSet());
            
            String token = jwtUtil.generateToken(user.getEmail(), user.getId(), roles);
            
            return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getEmail(), roles));

        } catch (Exception e) {
            System.err.println("LOGIN FAILED: " + e.getMessage());
            // Return 401 Unauthorized for bad credentials
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Invalid credentials"));
        }
    }
}