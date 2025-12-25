package com.example.demo.controller;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.service.InventoryLevelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryLevelService service;

    public InventoryController(InventoryLevelService service) { this.service = service; }

    @PostMapping("/update")
    public ResponseEntity<InventoryLevel> updateInventory(@RequestBody InventoryLevel inv) {
        return ResponseEntity.ok(service.createOrUpdateInventory(inv));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<InventoryLevel>> getInventoryByStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(service.getInventoryForStore(storeId));
    }
}