package com.example.demo.service.impl;
import com.example.demo.entity.InventoryLevel;
import com.example.demo.exception.*;
import com.example.demo.repository.*;
import com.example.demo.service.InventoryLevelService;
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

    public InventoryLevel createOrUpdateInventory(InventoryLevel i){
        if(i.getQuantity()<0) throw new BadRequestException("Negative not allowed");
        if(!storeRepo.existsById(i.getStore().getId())) throw new ResourceNotFoundException("Store not found");
        if(!productRepo.existsById(i.getProduct().getId())) throw new ResourceNotFoundException("Product not found");
        return invRepo.save(i);
    }

    public List<InventoryLevel> getInventoryForStore(Long id){ return invRepo.findByStore_Id(id); }
    public List<InventoryLevel> getInventoryForProduct(Long id){ return invRepo.findByProduct_Id(id); }
    public List<InventoryLevel> findAll(){ return invRepo.findAll(); }
    public InventoryLevel getSuggestionById(Long id){ return invRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found")); }
}
