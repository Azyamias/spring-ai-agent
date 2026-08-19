package org.cjj.saaassistant.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/debug")
public class DatabaseTestController {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseTestController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/database")
    public Map<String, Object> database() {

        Map<String, Object> result = new LinkedHashMap<>();

        // 查看当前连接的数据库
        String database = jdbcTemplate.queryForObject(
                "SELECT DATABASE()",
                String.class
        );

        result.put("database", database);

        // 查看 products 表
        List<Map<String, Object>> products =
                jdbcTemplate.queryForList("SELECT * FROM products");

        result.put("productsCount", products.size());
        result.put("products", products);

        return result;
    }
}