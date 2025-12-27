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
    private final StoreRepository storeRepo;

    public StoreServiceImpl(StoreRepository storeRepo) {
        this.storeRepo = storeRepo;
    }

    @Override
    public Store createStore(Store store) {
        if (storeRepo.findByStoreName(store.getStoreName()).isPresent()) {
            throw new BadRequestException("Store name already exists");
        }
        return storeRepo.save(store);
    }

    @Override
    public Store updateStore(Long id, Store update) {
        Store existing = getStoreById(id);
        if (!existing.getStoreName().equals(update.getStoreName()) && 
            storeRepo.findByStoreName(update.getStoreName()).isPresent()) {
            throw new BadRequestException("Store name already exists");
        }
        existing.setStoreName(update.getStoreName());
        existing.setAddress(update.getAddress());
        existing.setRegion(update.getRegion());
        return storeRepo.save(existing);
    }

    @Override
    public Store getStoreById(Long id) {
        return storeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found"));
    }

    @Override
    public void deactivateStore(Long id) {
        Store store = getStoreById(id);
        store.setActive(false);
        storeRepo.save(store);
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepo.findAll();
    }
}