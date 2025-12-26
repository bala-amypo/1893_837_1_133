package com.example.demo.service;

import com.example.demo.entity.TransferSuggestion;
import java.util.List;

public interface TransferSuggestionService {
    TransferSuggestion saveSuggestion(TransferSuggestion ts);
    TransferSuggestion getById(Long id);
    List<TransferSuggestion> getAll();
}
