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
    // Added ProductRepository
    private final ProductRepository productRepo; 

    public InventoryBalancerServiceImpl(TransferSuggestionRepository transferRepo,
                                        InventoryLevelRepository inventoryRepo,
                                        DemandForecastRepository forecastRepo,
                                        StoreRepository storeRepo,
                                        ProductRepository productRepo) { // Injected here
        this.transferRepo = transferRepo;
        this.inventoryRepo = inventoryRepo;
        this.forecastRepo = forecastRepo;
        this.storeRepo = storeRepo;
        this.productRepo = productRepo;
    }

    @Override
    public List<TransferSuggestion> generateSuggestions(Long productId) {
        // FIX for t61: Fetch product directly to check active status
        Product product = productRepo.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (!product.isActive()) {
            throw new BadRequestException("Product is inactive");
        }

        // Proceed with logic
        List<InventoryLevel> levels = inventoryRepo.findByProduct_Id(productId);
        List<TransferSuggestion> suggestions = new ArrayList<>();
        
        // Mock Logic: if we have 2+ inventory records, suggest a transfer
        if (levels.size() >= 2) {
            TransferSuggestion ts = new TransferSuggestion();
            ts.setProduct(product);
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