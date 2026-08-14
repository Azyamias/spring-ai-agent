package org.cjj.saaassistant.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.cjj.saaassistant.pojo.Order;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("select * from orders where id = #{id}")
    Order getOrderById(Integer id);

    @Select("seletc id from orders where user_id = #{user_id}")
    List<Integer> getOrdersByUserId(Integer userId);

    @Insert("insert into orders (user_id, total_price, status) values (#{user_id}, #{total_price}, #{status})")
    boolean insertOrder(Order order);

    @Update("update orders set user_id = #{user_id}, total_price = #{total_price}, status = #{status}")
    boolean updateOrder(Order order);
}
