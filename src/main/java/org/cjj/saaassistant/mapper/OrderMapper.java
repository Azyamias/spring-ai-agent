package org.cjj.saaassistant.mapper;

import org.apache.ibatis.annotations.*;
import org.cjj.saaassistant.pojo.Order;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("SELECT * FROM orders WHERE id = #{id}")
    @Results(id = "orderResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "totalPrice", column = "total_price"),
            @Result(property = "status", column = "status"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(
                    property = "orderProducts",
                    column = "id",
                    many = @Many(
                            select = "org.cjj.saaassistant.mapper.OrderProductMapper.selectByOrderId"
                    )
            )
    })
    Order selectOrderById(Integer id);

    @Select("""
            SELECT *
            FROM orders
            ORDER BY create_time DESC
            """)
    List<Order> selectAllOrders();

    @Select("""
            SELECT *
            FROM orders
            WHERE user_id = #{userId}
            ORDER BY create_time DESC
            """)
    List<Order> selectOrdersByUserId(Integer userId);

    @Insert("""
            INSERT INTO orders
            (user_id, total_price, status)
            VALUES
            (#{userId}, #{totalPrice}, #{status})
            """)
    @Options(
            useGeneratedKeys = true,
            keyProperty = "id",
            keyColumn = "id"
    )
    boolean insertOrder(Order order);

    @Update("""
            UPDATE orders
            SET status = #{status}
            WHERE id = #{id}
            """)
    boolean updateOrder(Order order);

    @Delete("""
            DELETE FROM orders
            WHERE id = #{id}
            """)
    boolean deleteOrderById(Integer id);
}