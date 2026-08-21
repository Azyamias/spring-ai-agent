package org.cjj.saaassistant.mapper;

import org.apache.ibatis.annotations.*;
import org.cjj.saaassistant.pojo.Product;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("select * from products where id = #{id}")
    Product selectProductById(Integer id);

    @Select("SELECT * FROM products WHERE name LIKE CONCAT('%', #{keyword}, '%')")
    List<Product> selectByNameLike(String keyword);

    @Select("select * from products")
    List<Product> selectAllProducts();

    @Insert("insert into products (name, price, stock) values (#{name}, #{price}, #{stock})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean insertProduct(Product product);

    @Update("update products set name = #{name}, price = #{price}, stock = #{stock} where id = #{id}")
    boolean updateProduct(Product product);

    @Delete("delete from products where id = #{id}")
    boolean deleteProductById(Integer id);
}
