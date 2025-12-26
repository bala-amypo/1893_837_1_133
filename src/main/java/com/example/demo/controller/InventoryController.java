package com.example.demo.controller;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService service;

    public InventoryController(InventoryService service) { this.service = service; }

    @PostMapping("/update")
    public ResponseEntity<InventoryLevel> updateInventory(@RequestParam Long storeId, 
                                                          @RequestParam Long productId, 
                                                          @RequestParam Integer quantity) {
        return ResponseEntity.ok(service.updateInventory(storeId, productId, quantity));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<InventoryLevel>> getInventoryByStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(service.getInventoryByStore(storeId));
    }
}