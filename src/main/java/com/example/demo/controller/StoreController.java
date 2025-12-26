package com.example.demo.controller;

import com.example.demo.entity.Store;
import com.example.demo.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService svc;

    public StoreController(StoreService svc) { this.svc = svc; }

    @PostMapping
    public ResponseEntity<Store> create(@RequestBody Store store) {
        return ResponseEntity.ok(svc.createStore(store));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Store> get(@PathVariable Long id) {
        return ResponseEntity.ok(svc.getStoreById(id));
    }

    @GetMapping
    public ResponseEntity<List<Store>> list() {
        return ResponseEntity.ok(svc.getAllStores());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Store> update(@PathVariable Long id, @RequestBody Store update) {
        return ResponseEntity.ok(svc.updateStore(id, update));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivate(@PathVariable Long id) {
        svc.deactivateStore(id);
        return ResponseEntity.ok().build();
    }
}
