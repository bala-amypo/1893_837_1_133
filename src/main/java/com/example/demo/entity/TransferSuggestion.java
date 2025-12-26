package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transfer_suggestions")
public class TransferSuggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Store sourceStore;
    @ManyToOne
    private Store targetStore;
    @ManyToOne
    private Product product;
    
    private Integer quantity;
    private String priority;
    private String reason;
    private String status = "PENDING";
    private LocalDateTime generatedAt;

    @PrePersist
    public void prePersist() {
        this.generatedAt = LocalDateTime.now();
        if(this.status == null) this.status = "PENDING";
    }

    // Manual Aliases for Tests
    public void setSuggestedQuantity(Integer q) { this.quantity = q; }
    public Integer getSuggestedQuantity() { return quantity; }
}