package com.example.demo.service.impl;

import com.example.demo.entity.Store;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.StoreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    
    private final StoreRepository storeRepository;
    
    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }
    
    @Override
    public Store createStore(Store store) {
        Store existing = storeRepository.findByStoreName(store.getStoreName());
        if (existing != null) {
            throw new BadRequestException("Store name already exists");
        }
        return storeRepository.save(store);
    }
    
    @Override
    public Store getStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
    }
    
    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }
    
    @Override
    public Store updateStore(Long id, Store update) {
        Store store = getStoreById(id);
        store.setStoreName(update.getStoreName());
        store.setAddress(update.getAddress());
        store.setRegion(update.getRegion());
        store.setActive(update.isActive());
        return storeRepository.save(store);
    }
    
    @Override
    public void deactivateStore(Long id) {
        Store store = getStoreById(id);
        store.setActive(false);
        storeRepository.save(store);
    }
}