package com.example.demo4.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo4.model.Category;
import com.example.demo4.repository.CategoryRepository;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  public Optional<Category> findById(Long id) {
    return categoryRepository.findById(id);
  }

  public Category create(Category category) {
    return categoryRepository.save(category);
  }

  public Optional<Category> update(Long id, Category input) {
    Optional<Category> existing = categoryRepository.findById(id);
    if (existing.isEmpty()) {
      return Optional.empty();
    }
    Category category = existing.get();
    category.setName(input.getName());
    return Optional.of(categoryRepository.save(category));
  }

  public boolean delete(Long id) {
    if (!categoryRepository.existsById(id)) {
      return false;
    }
    categoryRepository.deleteById(id);
    return true;
  }

  public Map<Long, String> getNameMap() {
    Map<Long, String> map = new LinkedHashMap<>();
    for (Category category : categoryRepository.findAll()) {
      map.put(category.getId(), category.getName());
    }
    return map;
  }
}
