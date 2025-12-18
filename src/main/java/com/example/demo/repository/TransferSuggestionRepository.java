package com.example.demo.repository;

import com.example.demo.entity.TransferSuggestion;
import com.example.demo.entity.Store;
import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransferSuggestionRepository extends JpaRepository<TransferSuggestion, Long> {
    List<TransferSuggestion> findBySourceStoreAndProduct(Store source, Product product);
}
