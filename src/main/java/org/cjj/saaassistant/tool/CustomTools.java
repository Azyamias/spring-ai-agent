package org.cjj.saaassistant.tool;

import org.cjj.saaassistant.pojo.User;
import org.cjj.saaassistant.service.OrderService;
import org.cjj.saaassistant.service.ProductService;
import org.cjj.saaassistant.service.UserService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomTools {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @Tool(description = "根据用户id查询用户")
    public User getUserById(Integer id) {
        return userService.getUserById(id);
    }
}
