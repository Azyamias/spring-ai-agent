package org.cjj.saaassistant.service;

import org.cjj.saaassistant.pojo.Product;

import java.math.BigDecimal;

public interface ProductService {

    // 通过id查询商品
    Product getProductById(Integer id);

    // 通过商品id查询价格
    BigDecimal getProductPriceById(Integer id);

    // 增加商品种类
    boolean addProduct(Product product);

    // 更新商品信息
    boolean updateProduct(Product product);
}
