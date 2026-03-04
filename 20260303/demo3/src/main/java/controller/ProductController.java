package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import model.Category;
import model.Product;
import service.CategoryService;
import service.ProductService;

@Controller
@RequestMapping({ "/products", "/product" })
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping({ "", "/", "/products" })
    public String index(
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {
        model.addAttribute("listproduct", productService.search(keyword));
        model.addAttribute("keyword", keyword == null ? "" : keyword);
        return "product/products";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @PostMapping("/create")
    public String create(
            @Valid Product newProduct,
            BindingResult result,
            @RequestParam("category_id") int categoryId,
            @RequestParam("imageProduct") MultipartFile imageProduct,
            Model model) {
        Category selectedCategory = categoryService.get(categoryId);
        newProduct.setCategory(selectedCategory);

        if (result.hasErrors()) {
            model.addAttribute("product", newProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }

        productService.updateImage(newProduct, imageProduct);
        productService.add(newProduct);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Product find = productService.get(id);
        if (find == null) {
            return "error/404";
        }

        model.addAttribute("product", find);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    @PostMapping("/edit")
    public String edit(
            @Valid Product editProduct,
            BindingResult result,
            @RequestParam("category_id") int categoryId,
            @RequestParam("imageProduct") MultipartFile imageProduct,
            Model model) {
        Category selectedCategory = categoryService.get(categoryId);
        editProduct.setCategory(selectedCategory);

        if (result.hasErrors()) {
            model.addAttribute("product", editProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }

        if (imageProduct != null && !imageProduct.isEmpty()) {
            productService.updateImage(editProduct, imageProduct);
        }

        productService.update(editProduct);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }

}
