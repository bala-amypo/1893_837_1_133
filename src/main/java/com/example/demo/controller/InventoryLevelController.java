package com.example.demo.controller;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.service.InventoryLevelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryLevelController {
    private final InventoryLevelService service;
    public InventoryLevelController(InventoryLevelService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<InventoryLevel> createOrUpdate(@RequestBody InventoryLevel inv) {
        return ResponseEntity.ok(service.createOrUpdateInventory(inv));
    }
}