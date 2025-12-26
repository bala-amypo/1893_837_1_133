package com.example.demo.repository;
import com.example.demo.entity.TransferSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransferSuggestionRepository extends JpaRepository<TransferSuggestion, Long> {
    // Fix: Added missing method
    List<TransferSuggestion> findByProduct_Id(Long productId);
    
    // Also likely needed based on other tests
    List<TransferSuggestion> findBySourceStoreId(Long storeId);
}