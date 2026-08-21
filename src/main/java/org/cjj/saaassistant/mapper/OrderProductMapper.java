package org.cjj.saaassistant.mapper;

import org.apache.ibatis.annotations.*;
import org.cjj.saaassistant.pojo.OrderProduct;

import java.util.List;

@Mapper
public interface OrderProductMapper {
    @Select("""
            SELECT *
            FROM order_products
            WHERE order_id = #{orderId}
            """)
    List<OrderProduct> selectByOrderId(Integer orderId);

    @Insert("""
            INSERT INTO order_products
            (
                order_id,
                product_id,
                product_name,
                product_price,
                quantity
            )
            VALUES
            (
                #{orderId},
                #{productId},
                #{productName},
                #{productPrice},
                #{quantity}
            )
            """)
    @Options(
            useGeneratedKeys = true,
            keyProperty = "id",
            keyColumn = "id"
    )
    boolean insertOrderProduct(OrderProduct orderProduct);

    @Delete("""
            DELETE FROM order_products
            WHERE order_id = #{orderId}
            """)
    boolean deleteByOrderId(Integer orderId);
}