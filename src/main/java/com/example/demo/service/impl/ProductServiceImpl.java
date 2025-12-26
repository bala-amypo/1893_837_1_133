package com.example.demo.service.impl;
import com.example.demo.entity.Product;
import com.example.demo.exception.*;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repo;
    public ProductServiceImpl(ProductRepository repo){ this.repo=repo; }

    public Product createProduct(Product p){
        if(repo.findBySku(p.getSku()).isPresent())
            throw new BadRequestException("SKU already exists");
        if(p.getName()==null || p.getName().isBlank())
            throw new BadRequestException("Product name required");
        return repo.save(p);
    }

    public void deactivateProduct(Long id){
        Product p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        p.setActive(false);
        repo.save(p);
    }

    public Product getProductById(Long id){ return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found")); }
    public List<Product> findAll(){ return repo.findAll(); }
}
