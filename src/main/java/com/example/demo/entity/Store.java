package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "store", uniqueConstraints = @UniqueConstraint(columnNames = "storeName"))
public class Store {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String storeName;
    private String address;
    private String region;
    private boolean active = true;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
