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
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String storeName;
    private String address;
    private String region;
    private Integer capacity;
    private boolean active = true;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // Manual Aliases needed for Tests (Lombok won't generate these specific names)
    public String getName() { return storeName; }
    public void setName(String name) { this.storeName = name; }
    
    public String getLocation() { return address; }
    public void setLocation(String location) { this.address = location; }
}