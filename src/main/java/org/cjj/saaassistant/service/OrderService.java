package org.cjj.saaassistant.service;

import org.cjj.saaassistant.pojo.CreateOrderRequest;
import org.cjj.saaassistant.pojo.Order;
import org.cjj.saaassistant.pojo.User;

import java.util.List;

public interface OrderService {
    Order getOrderById(Integer id);

    List<Order> getAllOrders();

    List<Order> getAllOrdersByUser(User user);

    Order createOrder(CreateOrderRequest request);

    boolean updateOrder(Order order);

    boolean deleteOrderById(Integer id);
}