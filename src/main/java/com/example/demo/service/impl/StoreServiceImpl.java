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
    private final StoreRepository repo;

    public StoreServiceImpl(StoreRepository repo) { this.repo = repo; }

    @Override
    public Store createStore(Store store) {
        if (store.getStoreName() != null && repo.findByStoreName(store.getStoreName()) != null) {
            throw new BadRequestException("Store name already exists");
        }
        if (store.isActive() == null) store.setActive(true);
        return repo.save(store);
    }

    @Override
    public Store getStoreById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found"));
    }

    @Override
    public List<Store> getAllStores() { return repo.findAll(); }

    @Override
    public Store updateStore(Long id, Store update) {
        Store s = getStoreById(id);
        if (update.getStoreName() != null) s.setStoreName(update.getStoreName());
        if (update.getAddress() != null) s.setAddress(update.getAddress());
        if (update.getRegion() != null) s.setRegion(update.getRegion());
        return repo.save(s);
    }

    // Fix: Added missing method implementation
    @Override
    public void deactivateStore(Long id) {
        Store s = getStoreById(id);
        s.setActive(false);
        repo.save(s);
    }
}