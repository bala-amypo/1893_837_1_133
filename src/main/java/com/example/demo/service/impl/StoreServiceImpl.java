package com.example.demo.service;

import com.example.demo.entity.Store;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StoreRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository repo;
    public StoreServiceImpl(StoreRepository repo){ this.repo = repo; }

    @Override
    public Store createStore(Store store){
        if(store.getStoreName() == null || store.getStoreName().isBlank())
            throw new BadRequestException("Store name required");
        return repo.save(store);
    }

    @Override
    public Store updateStore(Long id, Store update){
        Store s = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        s.setStoreName(update.getStoreName());
        s.setAddress(update.getAddress());
        s.setRegion(update.getRegion());
        s.setActive(update.isActive());
        return repo.save(s);
    }

    @Override
    public void deactivateStore(Long id){
        Store s = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        s.setActive(false);
        repo.save(s);
    }

    @Override
    public Store getStoreById(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
    }

    @Override
    public List<Store> findAll(){ return repo.findAll(); }
}
