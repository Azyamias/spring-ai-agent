package org.cjj.saaassistant.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
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
            @Result(property = "orderProducts", column = "id",
                    many = @Many(select = "org.cjj.saaassistant.mapper.OrderProductMapper.selectByOrderId"))
    })
    Order selectOrderById(Integer id);

    @Select("select * from orders where user_id = #{user_id} order by create_time DESC")
    List<Order> selectOrdersByUserId(Integer userId);

    @Select("select * from orders")
    List<Order> selectAllOrders();

    @Insert("insert into orders (user_id, total_price, status) values (#{user_id}, #{total_price}, #{status})")
    boolean insertOrder(Order order);

    @Update("update orders set user_id = #{user_id}, total_price = #{total_price}, status = #{status}")
    boolean updateOrder(Order order);
}
