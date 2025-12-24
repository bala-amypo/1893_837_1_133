package com.example.demo.controller;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.service.InventoryLevelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryLevelController {

    private final InventoryLevelService inventoryService;

    public InventoryLevelController(InventoryLevelService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public InventoryLevel createOrUpdate(@RequestBody InventoryLevel inventory) {
        return inventoryService.createOrUpdateInventory(inventory);
    }

    @GetMapping("/store/{storeId}")
    public List<InventoryLevel> getByStore(@PathVariable Long storeId) {
        return inventoryService.getInventoryForStore(storeId);
    }

    @GetMapping("/product/{productId}")
    public List<InventoryLevel> getByProduct(@PathVariable Long productId) {
        return inventoryService.getInventoryForProduct(productId);
    }
}
