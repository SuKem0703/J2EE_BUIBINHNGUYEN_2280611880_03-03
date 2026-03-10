package com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.repository;

import com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.model.Category;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Category> mapper = (rs, rowNum) ->
            new Category(rs.getInt("id"), rs.getString("name"));

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Category> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name FROM categories ORDER BY id",
                mapper
        );
    }

    public Category findById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT id, name FROM categories WHERE id = ?",
                    mapper,
                    id
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}

