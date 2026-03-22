package com.example.demo4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo4.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
