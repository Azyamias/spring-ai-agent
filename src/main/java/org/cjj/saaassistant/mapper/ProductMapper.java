package org.cjj.saaassistant.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.cjj.saaassistant.pojo.Product;

@Mapper
public interface ProductMapper {

    @Select("select * from product where id = #{id}")
    Product selectProductById(@Param("id") int id);
}
