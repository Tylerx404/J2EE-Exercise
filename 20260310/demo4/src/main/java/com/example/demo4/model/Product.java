package com.example.demo4.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Ten san pham khong duoc de trong")
  @Size(min = 2, max = 120, message = "Ten san pham phai tu 2 den 120 ky tu")
  @Column(nullable = false, length = 120)
  private String name;

  @NotNull(message = "Gia khong duoc de trong")
  @Positive(message = "Gia phai lon hon 0")
  @Column(nullable = false)
  private Long price;

  @NotNull(message = "Danh muc khong duoc de trong")
  @Column(nullable = false)
  private Long categoryId;

  @NotBlank(message = "Hinh anh khong duoc de trong")
  @Size(max = 500, message = "Duong dan hinh anh toi da 500 ky tu")
  @Column(nullable = false, length = 500)
  private String imageUrl;

  @Transient
  private Double discountedPrice;

  public Product() {
    // for JPA mapping
  }

  public Product(String name, Long price, Long categoryId, String imageUrl) {
    this.name = name;
    this.price = price;
    this.categoryId = categoryId;
    this.imageUrl = imageUrl;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Long getPrice() {
    return price;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public Double getDiscountedPrice() {
    return discountedPrice;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void setDiscountedPrice(Double discountedPrice) {
    this.discountedPrice = discountedPrice;
  }
}
