package org.cjj.saaassistant.service;

import org.cjj.saaassistant.pojo.Order;

import java.util.List;

public interface OrderService {

    // 根据id查找订单
    Order getOrderById(int id);

    // 创建订单
    boolean createOrder(Order order);

    // 更新订单信息
    boolean updateOrder(Order order);

    // 根据用户id获取历史订单
    List<Order> getAllOrdersById(Integer id);
}
