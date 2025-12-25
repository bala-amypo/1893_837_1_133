package com.example.demo.service;
import com.example.demo.entity.TransferSuggestion;
import java.util.List;

public interface TransferService {
    List<TransferSuggestion> suggestTransfers();
    TransferSuggestion approveTransfer(Long id);
}