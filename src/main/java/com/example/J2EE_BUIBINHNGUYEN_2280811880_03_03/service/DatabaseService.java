package com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DatabaseService {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean ping() {
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        return result != null && result == 1;
    }

    public Map<String, Object> info() {
        Map<String, Object> info = new LinkedHashMap<>();
        String dbName = jdbcTemplate.queryForObject("SELECT DATABASE()", String.class);
        Long productCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM products", Long.class);
        Long categoryCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM categories", Long.class);

        info.put("database", dbName);
        info.put("productCount", productCount);
        info.put("categoryCount", categoryCount);
        return info;
    }
}
