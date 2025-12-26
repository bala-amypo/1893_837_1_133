package com.example.demo.controller;

import com.example.demo.entity.TransferSuggestion;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suggestions")
public class TransferSuggestionController {

    private final InventoryBalancerService svc;

    public TransferSuggestionController(InventoryBalancerService svc) { this.svc = svc; }

    @PostMapping("/generate/{productId}")
    public ResponseEntity<List<TransferSuggestion>> generate(@PathVariable Long productId) {
        return ResponseEntity.ok(svc.generateSuggestions(productId));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<TransferSuggestion>> forStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(svc.getSuggestionsForStore(storeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferSuggestion> get(@PathVariable Long id) {
        return ResponseEntity.ok(svc.getSuggestionById(id));
    }
}
