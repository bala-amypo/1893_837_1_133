package com.example.demo.controller;

import com.example.demo.model.InventoryItem;
import com.example.demo.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<InventoryItem> getAll() {
        return service.getAll();
    }

    @PostMapping
    public InventoryItem create(@RequestBody InventoryItem item) {
        return service.addItem(item);
    }
}
