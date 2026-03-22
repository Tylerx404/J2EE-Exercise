package com.example.demo4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo4.model.Product;
import com.example.demo4.repository.ProductRepository;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> findAll() {
    return productRepository.findAll();
  }

  public Optional<Product> findById(Long id) {
    return productRepository.findById(id);
  }

  public Product create(Product product) {
    return productRepository.save(product);
  }

  public Optional<Product> update(Long id, Product input) {
    Optional<Product> existing = productRepository.findById(id);
    if (existing.isEmpty()) {
      return Optional.empty();
    }
    Product product = existing.get();
    product.setName(input.getName());
    product.setPrice(input.getPrice());
    product.setCategoryId(input.getCategoryId());
    product.setImageUrl(input.getImageUrl());
    return Optional.of(productRepository.save(product));
  }

  public boolean delete(Long id) {
    if (!productRepository.existsById(id)) {
      return false;
    }
    productRepository.deleteById(id);
    return true;
  }
}
