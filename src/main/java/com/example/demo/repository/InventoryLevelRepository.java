package com.example.demo.repository;
import com.example.demo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventoryLevelRepository extends JpaRepository<InventoryLevel, Long> {
    List<InventoryLevel> findByStore_Id(Long storeId);
    List<InventoryLevel> findByProduct_Id(Long productId);
    InventoryLevel findByStoreAndProduct(Store store, Product product);
}