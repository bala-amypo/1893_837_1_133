package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.InventoryService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryLevelRepository inventoryRepo;
    private final StoreRepository storeRepo;
    private final ProductRepository productRepo;

    public InventoryServiceImpl(InventoryLevelRepository ir, StoreRepository sr, ProductRepository pr) {
        this.inventoryRepo = ir;
        this.storeRepo = sr;
        this.productRepo = pr;
    }

    @Override
    public InventoryLevel updateInventory(Long storeId, Long productId, Integer quantity) {
        Store store = storeRepo.findById(storeId).orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        InventoryLevel level = inventoryRepo.findByStore_IdAndProduct_Id(storeId, productId)
                .orElse(new InventoryLevel());
        
        if (level.getId() == null) {
            level.setStore(store);
            level.setProduct(product);
        }
        level.setQuantity(quantity);
        return inventoryRepo.save(level);
    }

    @Override
    public List<InventoryLevel> getInventoryByStore(Long storeId) {
        return inventoryRepo.findByStore_Id(storeId);
    }
}