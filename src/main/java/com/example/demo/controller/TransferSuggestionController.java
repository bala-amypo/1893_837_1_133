package com.example.demo.controller;

import com.example.demo.entity.TransferSuggestion;
import com.example.demo.service.TransferSuggestionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/suggestions")
public class TransferSuggestionController {

    private final TransferSuggestionService service;

    public TransferSuggestionController(TransferSuggestionService service) {
        this.service = service;
    }

    @PostMapping
    public TransferSuggestion save(@RequestBody TransferSuggestion ts) {
        return service.saveSuggestion(ts);
    }

    @GetMapping("/{id}")
    public TransferSuggestion one(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<TransferSuggestion> all() {
        return service.getAll();
    }
}
