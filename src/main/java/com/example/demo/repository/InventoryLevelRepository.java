package com.example.demo.repository;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface InventoryLevelRepository extends JpaRepository<InventoryLevel, Long> {
    Optional<InventoryLevel> findByStoreAndProduct(Store store, Product product);
    List<InventoryLevel> findByStore_Id(Long storeId);
    List<InventoryLevel> findByProduct_Id(Long productId);
}