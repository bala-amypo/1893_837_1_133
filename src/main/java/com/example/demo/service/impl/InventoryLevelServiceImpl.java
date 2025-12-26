package com.example.demo.service;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.entity.Store;
import com.example.demo.entity.Product;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.InventoryLevelRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryLevelServiceImpl implements InventoryLevelService {
    private final InventoryLevelRepository invRepo;
    private final StoreRepository storeRepo;
    private final ProductRepository productRepo;

    public InventoryLevelServiceImpl(InventoryLevelRepository i, StoreRepository s, ProductRepository p){
        this.invRepo=i; this.storeRepo=s; this.productRepo=p;
    }

    @Override
    public InventoryLevel createOrUpdateInventory(InventoryLevel inv){
        if(inv.getQuantity() != null && inv.getQuantity() < 0)
            throw new BadRequestException("Negative inventory not allowed");

        Store s = storeRepo.findById(inv.getStore().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        Product p = productRepo.findById(inv.getProduct().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        InventoryLevel existing = invRepo.findByStore_IdAndProduct_Id(s.getId(),p.getId()).orElse(null);
        if(existing!=null){
            existing.setQuantity(inv.getQuantity());
            return invRepo.save(existing);
        }
        return invRepo.save(inv);
    }

    @Override
    public List<InventoryLevel> getInventoryForStore(Long storeId){
        if(!storeRepo.existsById(storeId))
            throw new ResourceNotFoundException("Store not found");
        return invRepo.findByStore_Id(storeId);
    }

    @Override
    public List<InventoryLevel> getInventoryForProduct(Long productId){
        if(!productRepo.existsById(productId))
            throw new ResourceNotFoundException("Product not found");
        return invRepo.findByProduct_Id(productId);
    }
}
