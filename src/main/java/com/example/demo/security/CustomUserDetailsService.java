package com.example.demo.security;

import com.example.demo.entity.UserAccount;
import com.example.demo.repository.UserAccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserAccountRepository repository;
    public CustomUserDetailsService(UserAccountRepository repository) { this.repository = repository; }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount user = repository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        // Handle role string, removing ROLE_ prefix if Spring adds it automatically later, 
        // but typically simple string works here.
        String role = user.getRole().startsWith("ROLE_") ? user.getRole().substring(5) : user.getRole();
        
        return User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .roles(role)
            .build();
    }
}