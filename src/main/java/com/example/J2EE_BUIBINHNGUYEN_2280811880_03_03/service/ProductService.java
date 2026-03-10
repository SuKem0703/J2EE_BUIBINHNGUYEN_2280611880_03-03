package com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.service;

import com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.model.Product;
import com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product get(int id) {
        return productRepository.findById(id);
    }

    public void add(Product newProduct) {
        int id = productRepository.insert(newProduct);
        newProduct.setId(id);
    }

    public void update(Product editProduct) {
        // Preserve current image if the edit form didn't upload a new one.
        if (editProduct.getImage() == null) {
            Product current = productRepository.findById(editProduct.getId());
            if (current != null) {
                editProduct.setImage(current.getImage());
            }
        }
        productRepository.update(editProduct);
    }

    public void delete(int id) {
        productRepository.deleteById(id);
    }

    public void updateImage(Product newProduct, MultipartFile imageProduct) {
        if (imageProduct != null && !imageProduct.isEmpty()) {
            try {
                // Dev-friendly: copied into target/classes so Spring Boot serves it from classpath static/.
                Path dirImages = Paths.get("target/classes/static/images");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }

                String newFileName = UUID.randomUUID() + "_" + imageProduct.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);

                Files.copy(imageProduct.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                newProduct.setImage(newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

