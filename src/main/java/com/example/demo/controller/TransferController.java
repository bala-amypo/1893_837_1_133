package com.example.demo.controller;

import com.example.demo.entity.TransferSuggestion;
import com.example.demo.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    private final TransferService service;

    public TransferController(TransferService service) { this.service = service; }

    @GetMapping("/suggest")
    public ResponseEntity<List<TransferSuggestion>> suggestTransfers() {
        return ResponseEntity.ok(service.suggestTransfers());
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<TransferSuggestion> approveTransfer(@PathVariable Long id) {
        return ResponseEntity.ok(service.approveTransfer(id));
    }
}