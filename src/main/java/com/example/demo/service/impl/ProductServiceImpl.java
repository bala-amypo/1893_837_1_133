package com.example.demo.service.impl;

import com.example.demo.entity.Product;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) { this.repo = repo; }

    @Override
    public Product createProduct(Product product) {
        if (product.getSku() != null && repo.findBySku(product.getSku()) != null) {
            throw new BadRequestException("SKU already exists");
        }
        // Fix: Removed 'if (product.isActive() == null)' because primitive boolean cannot be null
        return repo.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public List<Product> getAllProducts() { return repo.findAll(); }

    @Override
    public void deactivateProduct(Long id) {
        Product p = getProductById(id);
        p.setActive(false);
        repo.save(p);
    }
}