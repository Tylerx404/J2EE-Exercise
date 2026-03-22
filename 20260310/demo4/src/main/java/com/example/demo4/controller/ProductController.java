package com.example.demo4.controller;

import java.util.List;
import java.util.Map;
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

import com.example.demo4.model.Product;
import com.example.demo4.service.CategoryService;
import com.example.demo4.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;
  private final CategoryService categoryService;

  @Autowired
  public ProductController(ProductService productService, CategoryService categoryService) {
    this.productService = productService;
    this.categoryService = categoryService;
  }

  @GetMapping
  public String list(Model model) {
    List<Product> products = productService.findAll();
    Map<Long, String> categoryMap = categoryService.getNameMap();
    model.addAttribute("products", products);
    model.addAttribute("categoryMap", categoryMap);
    return "products/list";
  }

  @GetMapping("/new")
  public String showCreateForm(Model model) {
    prepareForm(model, new Product(), "/products", "Them moi");
    return "products/create";
  }

  @PostMapping
  public String create(
      @Valid @ModelAttribute("product") Product product,
      BindingResult bindingResult,
      Model model) {
    if (bindingResult.hasErrors()) {
      prepareForm(model, product, "/products", "Them moi");
      return "products/create";
    }
    productService.create(product);
    return "redirect:/products";
  }

  @GetMapping("/{id}/edit")
  public String showEditForm(@PathVariable Long id, Model model) {
    Optional<Product> product = productService.findById(id);
    if (product.isEmpty()) {
      return "redirect:/products";
    }
    prepareForm(model, product.get(), "/products/" + id, "Cap nhat");
    return "products/edit";
  }

  @PostMapping("/{id}")
  public String update(
      @PathVariable Long id,
      @Valid @ModelAttribute("product") Product product,
      BindingResult bindingResult,
      Model model) {
    if (bindingResult.hasErrors()) {
      prepareForm(model, product, "/products/" + id, "Cap nhat");
      return "products/edit";
    }
    Optional<Product> updated = productService.update(id, product);
    if (updated.isEmpty()) {
      return "redirect:/products";
    }
    return "redirect:/products";
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id) {
    productService.delete(id);
    return "redirect:/products";
  }

  @GetMapping("/{id}/delete")
  public String confirmDelete(@PathVariable Long id, Model model) {
    Optional<Product> product = productService.findById(id);
    if (product.isEmpty()) {
      return "redirect:/products";
    }
    Map<Long, String> categoryMap = categoryService.getNameMap();
    model.addAttribute("product", product.get());
    model.addAttribute("categoryMap", categoryMap);
    return "products/delete";
  }

  private void prepareForm(Model model, Product product, String action, String submitLabel) {
    model.addAttribute("product", product);
    model.addAttribute("categories", categoryService.findAll());
    model.addAttribute("formAction", action);
    model.addAttribute("submitLabel", submitLabel);
  }
}
