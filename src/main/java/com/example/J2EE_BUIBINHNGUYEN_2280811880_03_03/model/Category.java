package com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.model;

import jakarta.validation.constraints.NotBlank;

// Lombok removed; explicit constructors and getters/setters below
public class Category {
    private int id;
    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;

    public Category() {}

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}