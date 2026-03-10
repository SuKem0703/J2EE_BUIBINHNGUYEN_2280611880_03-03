package com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.repository;

import com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.model.Category;
import com.example.J2EE_BUIBINHNGUYEN_2280811880_03_03.model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Product> mapper = (rs, rowNum) -> {
        Integer categoryId = (Integer) rs.getObject("category_id");
        Category category = null;
        if (categoryId != null) {
            category = new Category(categoryId, rs.getString("category_name"));
        }
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("image"),
                rs.getLong("price"),
                category
        );
    };

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll() {
        return jdbcTemplate.query(
                """
                        SELECT p.id, p.name, p.image, p.price,
                               c.id AS category_id, c.name AS category_name
                        FROM products p
                        LEFT JOIN categories c ON p.category_id = c.id
                        ORDER BY p.id
                        """,
                mapper
        );
    }

    public Product findById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    """
                            SELECT p.id, p.name, p.image, p.price,
                                   c.id AS category_id, c.name AS category_name
                            FROM products p
                            LEFT JOIN categories c ON p.category_id = c.id
                            WHERE p.id = ?
                            """,
                    mapper,
                    id
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public int insert(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = prepareInsert(connection, product);
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key == null ? 0 : key.intValue();
    }

    private PreparedStatement prepareInsert(Connection connection, Product product) throws java.sql.SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO products (name, image, price, category_id) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        ps.setString(1, product.getName());
        ps.setString(2, product.getImage());
        ps.setLong(3, product.getPrice());
        if (product.getCategory() != null) {
            ps.setInt(4, product.getCategory().getId());
        } else {
            ps.setNull(4, Types.INTEGER);
        }
        return ps;
    }

    public void update(Product product) {
        Objects.requireNonNull(product, "product");

        Integer categoryId = product.getCategory() == null ? null : product.getCategory().getId();
        jdbcTemplate.update(
                "UPDATE products SET name = ?, image = ?, price = ?, category_id = ? WHERE id = ?",
                ps -> {
                    ps.setString(1, product.getName());
                    ps.setString(2, product.getImage());
                    ps.setLong(3, product.getPrice());
                    if (categoryId != null) {
                        ps.setInt(4, categoryId);
                    } else {
                        ps.setNull(4, Types.INTEGER);
                    }
                    ps.setInt(5, product.getId());
                }
        );
    }

    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM products WHERE id = ?", id);
    }
}

