package org.cjj.saaassistant.tool;

import lombok.extern.slf4j.Slf4j;
import org.cjj.saaassistant.pojo.Order;
import org.cjj.saaassistant.pojo.Product;
import org.cjj.saaassistant.pojo.User;
import org.cjj.saaassistant.service.OrderService;
import org.cjj.saaassistant.service.ProductService;
import org.cjj.saaassistant.service.UserService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CustomTools {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @Tool(description = "根据用户id或用户名查询用户，两者至少提供一个，未提供的参数使用null传入")
    public User getUserByIdOrName(
            @ToolParam(description = "用户id（正整数）") Integer id,
            @ToolParam(description = "用户名（字符串）") String name
    ) {
        log.info("---getUserByIdOrName id:{}, name:{}---", id, name);
        return userService.getUserByIdOrUsername(id, name);
    }

    @Tool(description = "查询所有用户")
    public List<User> getAllUsers() {
        log.info("---getAllUsers---");
        return userService.getAllUsers();
    }

    @Tool(description = "根据商品id或商品名查询商品，两者至少提供一个，未提供的参数使用null传入")
    public Product getProductByIdOrName(
            @ToolParam(description = "商品id（正整数）") Integer id
    ) {
        log.info("---getProductById id:{}---", id);
        return productService.getProductById(id);
    }

    @Tool(description = "通过关键词查询商品，支持模糊查询")
    public List<Product> getProductByKeyword(String keyword) {
        log.info("---getProductByKeyword keyword:{}---", keyword);
        return productService.getProductByKeyword(keyword);
    }

    @Tool(description = "查询所有商品")
    public List<Product> getAllProducts() {
        log.info("---getAllProducts---");
        return productService.getAllProducts();
    }

    @Tool(description = "根据订单id查询订单")
    public Order getOrderById(
            @ToolParam(description = "订单id（正整数）") Integer id) {
        log.info("---getOrderById id:{}---", id);
        return orderService.getOrderById(id);
    }

    @Tool(description = "查询用户历史订单")
    public List<Order> getAllOrdersByUser(User user) {
        log.info("---getAllOrdersByUser---");
        return orderService.getAllOrdersByUser(user);
    }
}
