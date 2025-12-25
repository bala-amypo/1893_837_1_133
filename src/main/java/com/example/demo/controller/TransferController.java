package com.example.demo.controller;

import com.example.demo.entity.TransferSuggestion;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    private final InventoryBalancerService service;

    public TransferController(InventoryBalancerService service) { this.service = service; }

    @GetMapping("/suggest")
    public ResponseEntity<List<TransferSuggestion>> suggestTransfers(@RequestParam Long productId) {
        return ResponseEntity.ok(service.generateSuggestions(productId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferSuggestion> getTransferById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSuggestionById(id));
    }
}