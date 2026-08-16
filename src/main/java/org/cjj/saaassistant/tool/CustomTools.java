package org.cjj.saaassistant.tool;

import lombok.extern.slf4j.Slf4j;
import org.cjj.saaassistant.pojo.Order;
import org.cjj.saaassistant.pojo.Product;
import org.cjj.saaassistant.pojo.User;
import org.cjj.saaassistant.service.OrderService;
import org.cjj.saaassistant.service.ProductService;
import org.cjj.saaassistant.service.UserService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

@Component
@Slf4j
public class CustomTools {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @Tool(description = "根据用户id查询用户")
    public User getUserById(Integer id) {
        log.info("getUserById");
        return userService.getUserById(id);
    }

    @Tool(description = "根据商品id查询商品")
    public Product getProductById(Integer id) {
        return productService.getProductById(id);
    }

    @Tool(description = "根据订单id查询订单")
    public Order getOrderById(Integer id) {
        return orderService.getOrderById(id);
    }

    @Tool(description = "根据用户id查询该用户的历史订单")
    public List<Integer> getProductIdsByOrderId(Integer id) {
        return orderService.getAllOrdersById(id);
    }

    @Tool(description = "根据商品id查询商品价格")
    public BigDecimal getProductPriceById(Integer id) {
        return productService.getProductById(id).getPrice();
    }

    @Tool(description = "更新用户信息")
    public boolean updateUser(User user) {
        return userService.updateUser(user);
    }
}
