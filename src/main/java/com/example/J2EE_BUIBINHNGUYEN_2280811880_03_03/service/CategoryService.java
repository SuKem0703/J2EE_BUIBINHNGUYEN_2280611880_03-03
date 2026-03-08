package com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.service;

import com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.model.Category;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private List<Category> listCategory = new ArrayList<>();

    public CategoryService() {
        // Khởi tạo dữ liệu mẫu giống như trong file thiết kế
        listCategory.add(new Category(1, "Điện thoại"));
        listCategory.add(new Category(2, "Laptop"));
        listCategory.add(new Category(3, "Phụ kiện"));
    }

    // Lấy toàn bộ danh sách danh mục để hiển thị lên Dropdown
    public List<Category> getAll() {
        return listCategory;
    }

    // Tìm danh mục theo ID để gán vào sản phẩm khi lưu
    public Category get(int id) {
        return listCategory.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}