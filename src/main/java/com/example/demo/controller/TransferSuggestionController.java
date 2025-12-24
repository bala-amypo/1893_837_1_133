package com.example.demo.controller;

import com.example.demo.entity.TransferSuggestion;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suggestions")
public class TransferSuggestionController {

    private final InventoryBalancerService balancerService;

    public TransferSuggestionController(InventoryBalancerService balancerService) {
        this.balancerService = balancerService;
    }

    @PostMapping("/generate/{productId}")
    public List<TransferSuggestion> generate(@PathVariable Long productId) {
        return balancerService.generateSuggestions(productId);
    }

    @GetMapping("/{id}")
    public TransferSuggestion getById(@PathVariable Long id) {
        return balancerService.getSuggestionById(id);
    }

    @GetMapping("/store/{storeId}")
    public List<TransferSuggestion> getForStore(@PathVariable Long storeId) {
        return balancerService.getSuggestionsForStore(storeId);
    }
}
