package com.example.demo.dto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterRequestDto { 
    private String email; 
    private String fullName; 
    private String password; 
    private String role; 
}