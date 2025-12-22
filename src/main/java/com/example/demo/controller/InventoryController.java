package com.example.demo.controller;

import com.example.demo.model.InventoryItem;
import com.example.demo.service.InventoryService;
import com.example.demo.service.InventoryBalanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService service;
    private final InventoryBalanceService balanceService;

    public InventoryController(InventoryService service, InventoryBalanceService balanceService) {
        this.service = service;
        this.balanceService = balanceService;
    }

    @GetMapping
    public List<InventoryItem> getAll() {
        return service.getAll();
    }

    @PostMapping
    public InventoryItem create(@RequestBody InventoryItem item) {
        return service.addItem(item);
    }

    @GetMapping("/balance")
    public List<String> balanceStock() {
        return balanceService.recommendTransfers();
    }
}
