package com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.service;

import com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.model.Category;
import com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category get(int id) {
        return categoryRepository.findById(id);
    }
}

