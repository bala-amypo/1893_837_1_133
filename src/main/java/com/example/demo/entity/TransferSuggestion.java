package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transfer_suggestions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferSuggestion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "source_store_id", nullable = false)
    private Store sourceStore;
    
    @ManyToOne
    @JoinColumn(name = "target_store_id", nullable = false)
    private Store targetStore;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(nullable = false)
    private Integer suggestedQuantity;
    
    @Column
    private String priority = "MEDIUM";
    
    @Column(nullable = false)
    private LocalDateTime generatedAt;
    
    @Column
    private String status = "PENDING";
    
    @Column
    private String reason;
    
    @PrePersist
    public void prePersist() {
        this.generatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "PENDING";
        }
    }
}