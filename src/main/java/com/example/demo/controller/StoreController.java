package com.example.demo.controller;

import com.example.demo.entity.Store;
import com.example.demo.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService service;
    public StoreController(StoreService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Store> create(@RequestBody Store store) {
        return ResponseEntity.ok(service.createStore(store));
    }

    @GetMapping
    public ResponseEntity<List<Store>> getAll() {
        return ResponseEntity.ok(service.getAllStores());
    }
}