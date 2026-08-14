package org.cjj.saaassistant.service.impl;

import org.cjj.saaassistant.mapper.ProductMapper;
import org.cjj.saaassistant.pojo.Product;
import org.cjj.saaassistant.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public Product getProductById(Integer id) {
        if (id == null) {
            return null;
        }
        return productMapper.selectProductById(id);
    }

    @Override
    public BigDecimal getProductPriceById(Integer id) {
        if (id == null) {
            return null;
        }
        return productMapper.selectProductPriceById(id);
    }

    @Override
    @Transactional
    public boolean addProduct(Product product) {
        if (product == null || product.getName() == null || product.getPrice() == null || product.getStock() == null) {
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
}
