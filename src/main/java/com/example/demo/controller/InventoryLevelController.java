package com.example.demo.controller;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.service.InventoryLevelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryLevelController {

    private final InventoryLevelService svc;

    public InventoryLevelController(InventoryLevelService svc) { this.svc = svc; }

    @PostMapping
    public ResponseEntity<InventoryLevel> create(@RequestBody InventoryLevel inv) {
        return ResponseEntity.ok(svc.createOrUpdateInventory(inv));
    }

    @PutMapping("/update")
    public ResponseEntity<InventoryLevel> update(@RequestBody InventoryLevel inv) {
        return ResponseEntity.ok(svc.createOrUpdateInventory(inv));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<InventoryLevel>> byStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(svc.getInventoryForStore(storeId));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<InventoryLevel>> byProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(svc.getInventoryForProduct(productId));
    }
}
