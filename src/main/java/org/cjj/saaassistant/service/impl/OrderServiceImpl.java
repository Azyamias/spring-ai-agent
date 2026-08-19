package org.cjj.saaassistant.service.impl;

import org.cjj.saaassistant.mapper.OrderMapper;
import org.cjj.saaassistant.pojo.Order;
import org.cjj.saaassistant.pojo.User;
import org.cjj.saaassistant.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public Order getOrderById(Integer id) {
        if (id == null) {
            return null;
        }
        return orderMapper.selectOrderById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.selectAllOrders();
    }

    @Override
    public List<Order> getAllOrdersByUser(User user) {
        if (user == null || user.getId() == null) {
            return null;
        }
        return orderMapper.selectOrdersByUserId(user.getId());
    }

    /*@Override
    @Transactional
    public boolean createOrder(Order order) {
        if (order == null || order.getId() == null || order.getUserId() == null || order.getTotalPrice() == null || order.getStatus() == null) {
            return false;
        }
        return orderMapper.insertOrder(order);
    }*/

    @Override
    @Transactional
    public boolean updateOrder(Order order) {
        if (order == null || order.getId() == null || order.getUserId() == null || order.getTotalPrice() == null || order.getStatus() == null) {
            return false;
        }
        return orderMapper.updateOrder(order);
    }
}
