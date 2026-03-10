package com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.controller;

import com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.model.Category;
import com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.model.Product;
import com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.service.CategoryService;
import com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"/", "/products"})
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    // Hiển thị danh sách sản phẩm
    @GetMapping
    public String index(Model model) {
        model.addAttribute("listProduct", productService.getAll());
        return "product/product";
    }

    // Hiển thị form tạo mới sản phẩm
    @GetMapping("/create")
    public String create(Model model) {
        Product product = new Product();
        product.setCategory(new Category());
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    // Xử lý lưu sản phẩm mới
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("product") Product newProduct,
                         BindingResult result,
                         Model model,
                         @RequestParam("imageProduct") MultipartFile imageProduct,
                         @RequestParam("category.id") int categoryId) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }

        // Xử lý lưu ảnh và gán danh mục
        productService.updateImage(newProduct, imageProduct);
        Category selectedCategory = categoryService.get(categoryId);
        newProduct.setCategory(selectedCategory);

        productService.add(newProduct);
        return "redirect:/products";
    }

    // Hiển thị form chỉnh sửa sản phẩm
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Product find = productService.get(id);
        if (find == null) {
            return "error/404";
        }
        if (find.getCategory() == null) {
            find.setCategory(new Category());
        }
        model.addAttribute("product", find);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    // Xử lý cập nhật sản phẩm
    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("product") Product editProduct,
                       BindingResult result,
                       Model model,
                       @RequestParam("imageProduct") MultipartFile imageProduct,
                       @RequestParam("category.id") int categoryId) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }

        // Nếu có tải ảnh mới thì mới cập nhật ảnh
        if (!imageProduct.isEmpty()) {
            productService.updateImage(editProduct, imageProduct);
        }

        Category selectedCategory = categoryService.get(categoryId);
        editProduct.setCategory(selectedCategory);

        productService.update(editProduct);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }
}

