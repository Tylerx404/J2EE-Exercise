package com.example.demo5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo5.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
