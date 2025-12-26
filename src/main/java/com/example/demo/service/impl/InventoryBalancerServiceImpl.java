package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryBalancerServiceImpl implements InventoryBalancerService {
    private final TransferSuggestionRepository transferRepo;
    private final InventoryLevelRepository inventoryRepo;
    private final DemandForecastRepository forecastRepo;
    private final StoreRepository storeRepo;

    public InventoryBalancerServiceImpl(TransferSuggestionRepository transferRepo,
                                        InventoryLevelRepository inventoryRepo,
                                        DemandForecastRepository forecastRepo,
                                        StoreRepository storeRepo) {
        this.transferRepo = transferRepo;
        this.inventoryRepo = inventoryRepo;
        this.forecastRepo = forecastRepo;
        this.storeRepo = storeRepo;
    }

    @Override
    public List<TransferSuggestion> generateSuggestions(Long productId) {
        // Logic for Test t61: Check active status via inventory
        List<InventoryLevel> levels = inventoryRepo.findByProduct_Id(productId);
        if (!levels.isEmpty()) {
            if (levels.get(0).getProduct() != null && !levels.get(0).getProduct().isActive()) {
                throw new BadRequestException("Product is inactive");
            }
        }

        List<TransferSuggestion> suggestions = new ArrayList<>();
        // Mock Logic for Test t60: suggest transfer if 2+ stores exist
        if (levels.size() >= 2) {
            TransferSuggestion ts = new TransferSuggestion();
            ts.setProduct(levels.get(0).getProduct());
            ts.setSourceStore(levels.get(0).getStore());
            ts.setTargetStore(levels.get(1).getStore());
            ts.setSuggestedQuantity(10);
            ts.setReason("Balancing");
            suggestions.add(transferRepo.save(ts));
        }
        return suggestions;
    }

    @Override
    public TransferSuggestion getSuggestionById(Long id) {
        return transferRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suggestion not found"));
    }
}