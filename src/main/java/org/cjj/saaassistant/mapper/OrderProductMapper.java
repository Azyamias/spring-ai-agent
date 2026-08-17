package org.cjj.saaassistant.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.cjj.saaassistant.pojo.OrderProduct;

import java.util.List;

@Mapper
public interface OrderProductMapper {
    @Select("SELECT * FROM order_products WHERE order_id = #{orderId}")
    List<OrderProduct> selectByOrderId(Integer orderId);
}
