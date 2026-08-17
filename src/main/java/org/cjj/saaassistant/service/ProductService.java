package org.cjj.saaassistant.service;

import org.cjj.saaassistant.pojo.Product;

import java.util.List;

public interface ProductService {

    // 通过商品id或商品名查询商品
    Product getProductById(Integer id);

    // 商品名称模糊查询
    List<Product> getProductByKeyword(String keyword);

    // 查询所有商品
    List<Product> getAllProducts();
}
