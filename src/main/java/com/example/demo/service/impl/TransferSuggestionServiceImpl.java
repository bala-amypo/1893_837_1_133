package com.example.demo.service.impl;

import com.example.demo.entity.TransferSuggestion;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TransferSuggestionRepository;
import com.example.demo.service.TransferSuggestionService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransferSuggestionServiceImpl implements TransferSuggestionService {

    private final TransferSuggestionRepository repo;

    public TransferSuggestionServiceImpl(TransferSuggestionRepository repo) {
        this.repo = repo;
    }

    @Override
    public TransferSuggestion saveSuggestion(TransferSuggestion ts) {
        return repo.save(ts);
    }

    @Override
    public TransferSuggestion getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suggestion not found"));
    }

    @Override
    public List<TransferSuggestion> getAll() {
        return repo.findAll();
    }
}
