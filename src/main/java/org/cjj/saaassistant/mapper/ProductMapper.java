package org.cjj.saaassistant.mapper;

import org.apache.ibatis.annotations.*;
import org.cjj.saaassistant.pojo.Product;

import java.math.BigDecimal;

@Mapper
public interface ProductMapper {

    @Select("select * from products where id = #{id}")
    Product selectProductById(Integer id);

    @Select("select price from products where id = #{id}")
    BigDecimal selectProductPriceById(Integer id);

    @Insert("insert products (name, price, stock) values (#{name}, #{price}, #{stock})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean insertProduct(Product product);

    @Update("update products set name = #{name}, price = #{price}, stock = #{stock} where id = #{id}")
    boolean updateProduct(Product product);
}
