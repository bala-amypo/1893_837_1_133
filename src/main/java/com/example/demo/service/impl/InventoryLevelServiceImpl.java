package com.example.demo.service.impl;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.InventoryLevelRepository;
import com.example.demo.service.InventoryLevelService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryLevelServiceImpl implements InventoryLevelService {
    private final InventoryLevelRepository repo;

    public InventoryLevelServiceImpl(InventoryLevelRepository repo) {
        this.repo = repo;
    }

    @Override
    public InventoryLevel createOrUpdateInventory(InventoryLevel inv) {
        if (inv.getQuantity() < 0) {
            throw new BadRequestException("Quantity must be >= 0");
        }
        Optional<InventoryLevel> existing = repo.findByStoreAndProduct(inv.getStore(), inv.getProduct());
        if (existing.isPresent()) {
            InventoryLevel e = existing.get();
            e.setQuantity(inv.getQuantity());
            return repo.save(e);
        }
        return repo.save(inv);
    }

    @Override
    public List<InventoryLevel> getInventoryForStore(Long storeId) {
        return repo.findByStore_Id(storeId);
    }

    @Override
    public List<InventoryLevel> getInventoryForProduct(Long productId) {
        return repo.findByProduct_Id(productId);
    }
}