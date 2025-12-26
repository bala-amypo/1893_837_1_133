package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TransferSuggestion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne private Store sourceStore;
    @ManyToOne private Store targetStore;
    @ManyToOne private Product product;

    @Column(nullable = false)
    private Integer suggestedQuantity;

    private String priority = "MEDIUM";
    private String status = "PENDING";
    private LocalDateTime generatedAt;

    @PrePersist
    public void onGenerate() {
        this.generatedAt = LocalDateTime.now();
    }
}
