package org.cjj.saaassistant.service;

import org.cjj.saaassistant.pojo.Order;
import org.cjj.saaassistant.pojo.User;

import java.util.List;

public interface OrderService {

    // 根据id查找订单
    Order getOrderById(Integer id);

    // 获取所有订单
    List<Order> getAllOrders();

    // 获取用户历史订单
    List<Order> getAllOrdersByUser(User user);
}
