package org.cjj.saaassistant.service.impl;

import org.cjj.saaassistant.mapper.ProductMapper;
import org.cjj.saaassistant.pojo.Product;
import org.cjj.saaassistant.service.ProductService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final JdbcTemplate jdbcTemplate;

    public ProductServiceImpl(
            ProductMapper productMapper,
            JdbcTemplate jdbcTemplate
    ) {
        this.productMapper = productMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product getProductById(Integer id) {
        if (id == null) {
            return null;
        }
        return productMapper.selectProductById(id);
    }

    @Override
    public List<Product> getProductByKeyword(String keyword) {
        return productMapper.selectByNameLike(keyword);
    }

    @Override
    public List<Product> getAllProducts() {
        return productMapper.selectAllProducts();
    }

    @Override
    @Transactional
    public boolean addProduct(Product product) {

        System.out.println("========================================");
        System.out.println("当前数据库: "
                + jdbcTemplate.queryForObject(
                "SELECT DATABASE()",
                String.class
        ));
        System.out.println("========================================");

        if (product == null
                || product.getName() == null
                || product.getPrice() == null
                || product.getStock() == null) {
            return false;
        }

        return productMapper.insertProduct(product);
    }

    @Override
    @Transactional
    public boolean updateProduct(Product product) {
        if (product == null || product.getId() == null) {
            return false;
        }
        return productMapper.updateProduct(product);
    }

    @Override
    @Transactional
    public boolean deleteProduct(Integer id) {
        if (id == null) {
            return false;
        }
        return productMapper.deleteProductById(id);
    }
}