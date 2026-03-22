package com.example.demo4.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo4.model.Category;
import com.example.demo4.service.CategoryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public String list(Model model) {
    List<Category> categories = categoryService.findAll();
    model.addAttribute("categories", categories);
    return "categories/list";
  }

  @GetMapping("/new")
  public String showCreateForm(Model model) {
    prepareForm(model, new Category(), "/categories", "Them moi");
    return "categories/create";
  }

  @PostMapping
  public String create(
      @Valid @ModelAttribute("category") Category category,
      BindingResult bindingResult,
      Model model) {
    if (bindingResult.hasErrors()) {
      prepareForm(model, category, "/categories", "Them moi");
      return "categories/create";
    }
    categoryService.create(category);
    return "redirect:/categories";
  }

  @GetMapping("/{id}/edit")
  public String showEditForm(@PathVariable Long id, Model model) {
    Optional<Category> category = categoryService.findById(id);
    if (category.isEmpty()) {
      return "redirect:/categories";
    }
    prepareForm(model, category.get(), "/categories/" + id, "Cap nhat");
    return "categories/edit";
  }

  @PostMapping("/{id}")
  public String update(
      @PathVariable Long id,
      @Valid @ModelAttribute("category") Category category,
      BindingResult bindingResult,
      Model model) {
    if (bindingResult.hasErrors()) {
      prepareForm(model, category, "/categories/" + id, "Cap nhat");
      return "categories/edit";
    }
    Optional<Category> updated = categoryService.update(id, category);
    if (updated.isEmpty()) {
      return "redirect:/categories";
    }
    return "redirect:/categories";
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id) {
    categoryService.delete(id);
    return "redirect:/categories";
  }

  @GetMapping("/{id}/delete")
  public String confirmDelete(@PathVariable Long id, Model model) {
    Optional<Category> category = categoryService.findById(id);
    if (category.isEmpty()) {
      return "redirect:/categories";
    }
    model.addAttribute("category", category.get());
    return "categories/delete";
  }

  private void prepareForm(Model model, Category category, String action, String submitLabel) {
    model.addAttribute("category", category);
    model.addAttribute("formAction", action);
    model.addAttribute("submitLabel", submitLabel);
  }
}
