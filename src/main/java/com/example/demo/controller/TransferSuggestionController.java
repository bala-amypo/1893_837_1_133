package com.example.demo.controller;

import com.example.demo.service.InventoryBalancerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suggestions")
public class TransferSuggestionController {
    private final InventoryBalancerService service;
    public TransferSuggestionController(InventoryBalancerService service) { this.service = service; }

    @PostMapping("/generate/{productId}")
    public ResponseEntity<?> generate(@PathVariable Long productId) {
        return ResponseEntity.ok(service.generateSuggestions(productId));
    }
}