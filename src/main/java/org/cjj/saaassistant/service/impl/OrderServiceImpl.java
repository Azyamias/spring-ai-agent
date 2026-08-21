package org.cjj.saaassistant.service.impl;

import org.cjj.saaassistant.mapper.OrderMapper;
import org.cjj.saaassistant.mapper.OrderProductMapper;
import org.cjj.saaassistant.mapper.ProductMapper;
import org.cjj.saaassistant.mapper.UserMapper;
import org.cjj.saaassistant.pojo.*;
import org.cjj.saaassistant.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final OrderProductMapper orderProductMapper;

    private final ProductMapper productMapper;

    private final UserMapper userMapper;

    public OrderServiceImpl(
            OrderMapper orderMapper,
            OrderProductMapper orderProductMapper,
            ProductMapper productMapper,
            UserMapper userMapper
    ) {
        this.orderMapper = orderMapper;
        this.orderProductMapper = orderProductMapper;
        this.productMapper = productMapper;
        this.userMapper = userMapper;
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

    @Override
    @Transactional
    public Order createOrder(CreateOrderRequest request) {

        if (request == null
                || request.getUserId() == null
                || request.getProducts() == null
                || request.getProducts().isEmpty()) {

            throw new IllegalArgumentException(
                    "创建订单失败：订单信息不能为空"
            );
        }


        // 查询用户
        User user = userMapper.selectUserById(
                request.getUserId()
        );

        if (user == null) {

            throw new IllegalArgumentException(
                    "创建订单失败：用户不存在，用户id："
                            + request.getUserId()
            );
        }


        BigDecimal totalPrice = BigDecimal.ZERO;

        List<OrderProduct> orderProducts = new ArrayList<>();


        // 查询商品、检查库存、计算总价
        for (OrderProductRequest productRequest
                : request.getProducts()) {

            if (productRequest == null
                    || productRequest.getProductId() == null
                    || productRequest.getQuantity() == null
                    || productRequest.getQuantity() <= 0) {

                throw new IllegalArgumentException(
                        "创建订单失败：商品信息或购买数量不正确"
                );
            }


            Product product =
                    productMapper.selectProductById(
                            productRequest.getProductId()
                    );


            if (product == null) {

                throw new IllegalArgumentException(
                        "创建订单失败：商品不存在，商品id："
                                + productRequest.getProductId()
                );
            }


            // 检查库存
            if (product.getStock() == null
                    || product.getStock()
                    < productRequest.getQuantity()) {

                throw new IllegalArgumentException(
                        "创建订单失败：商品库存不足，商品："
                                + product.getName()
                                + "，当前库存："
                                + product.getStock()
                );
            }


            // 计算商品金额
            BigDecimal itemPrice =
                    product.getPrice().multiply(
                            BigDecimal.valueOf(
                                    productRequest.getQuantity()
                            )
                    );


            totalPrice = totalPrice.add(itemPrice);


            // 创建订单商品快照
            OrderProduct orderProduct =
                    new OrderProduct();

            orderProduct.setProductId(
                    product.getId()
            );

            orderProduct.setProductName(
                    product.getName()
            );

            orderProduct.setProductPrice(
                    product.getPrice()
            );

            orderProduct.setQuantity(
                    productRequest.getQuantity()
            );

            orderProducts.add(orderProduct);
        }


        // 创建订单
        Order order = new Order();

        order.setUserId(user.getId());

        order.setTotalPrice(totalPrice);

        order.setStatus("UNPAID");


        boolean result =
                orderMapper.insertOrder(order);


        if (!result || order.getId() == null) {

            throw new IllegalStateException(
                    "创建订单失败"
            );
        }


        // 创建订单商品并扣减库存
        for (OrderProduct orderProduct : orderProducts) {

            orderProduct.setOrderId(
                    order.getId()
            );


            boolean productResult =
                    orderProductMapper.insertOrderProduct(
                            orderProduct
                    );


            if (!productResult) {

                throw new IllegalStateException(
                        "创建订单商品失败"
                );
            }


            boolean stockResult =
                    productMapper.decreaseStock(
                            orderProduct.getProductId(),
                            orderProduct.getQuantity()
                    );


            if (!stockResult) {

                throw new IllegalStateException(
                        "商品库存扣减失败，商品id："
                                + orderProduct.getProductId()
                );
            }
        }


        order.setOrderProducts(
                orderProducts
        );

        return order;
    }

    @Override
    @Transactional
    public boolean updateOrder(Order order) {

        if (order == null
                || order.getId() == null
                || order.getStatus() == null) {

            return false;
        }

        // 查询原订单
        Order oldOrder =
                orderMapper.selectOrderById(order.getId());

        if (oldOrder == null) {
            return false;
        }

        String oldStatus = oldOrder.getStatus();

        String newStatus = order.getStatus();

        // 检查订单状态是否合法
        if (!"UNPAID".equals(newStatus)
                && !"PAID".equals(newStatus)
                && !"CANCELLED".equals(newStatus)) {

            throw new IllegalArgumentException(
                    "订单状态不正确，只支持：UNPAID、PAID、CANCELLED"
            );
        }

        // 已取消订单不能再次修改
        if ("CANCELLED".equals(oldStatus)) {

            throw new IllegalStateException(
                    "订单已经取消，不能继续修改"
            );
        }

        // 已支付订单不能修改成未支付
        if ("PAID".equals(oldStatus)
                && "UNPAID".equals(newStatus)) {

            throw new IllegalStateException(
                    "订单已经支付，不能修改为未支付"
            );
        }

        /*
         * ==========================================================
         * 订单取消
         * ==========================================================
         *
         * 只有：
         *
         * UNPAID -> CANCELLED
         *
         * 才需要恢复库存。
         */
        if ("UNPAID".equals(oldStatus)
                && "CANCELLED".equals(newStatus)) {

            // 获取订单商品明细
            List<OrderProduct> orderProducts =
                    oldOrder.getOrderProducts();

            if (orderProducts != null
                    && !orderProducts.isEmpty()) {

                for (OrderProduct orderProduct
                        : orderProducts) {

                    if (orderProduct.getProductId() == null
                            || orderProduct.getQuantity() == null
                            || orderProduct.getQuantity() <= 0) {

                        throw new IllegalStateException(
                                "订单商品信息异常，无法恢复库存"
                        );
                    }

                    // 恢复库存
                    boolean stockResult =
                            productMapper.increaseStock(
                                    orderProduct.getProductId(),
                                    orderProduct.getQuantity()
                            );

                    if (!stockResult) {

                        throw new IllegalStateException(
                                "取消订单失败：恢复商品库存失败，商品id："
                                        + orderProduct.getProductId()
                        );
                    }
                }
            }
        }

        // 最后修改订单状态
        return orderMapper.updateOrder(order);
    }

    @Override
    @Transactional
    public boolean deleteOrderById(Integer id) {

        if (id == null) {
            return false;
        }


        // 查询订单
        Order order =
                orderMapper.selectOrderById(id);


        if (order == null) {
            return false;
        }


        // 已支付订单禁止直接删除
        if ("PAID".equals(order.getStatus())) {

            throw new IllegalStateException(
                    "已支付订单不能删除"
            );
        }


        // 删除订单之前恢复库存
        if (order.getOrderProducts() != null) {

            for (OrderProduct orderProduct
                    : order.getOrderProducts()) {

                boolean stockResult =
                        productMapper.increaseStock(
                                orderProduct.getProductId(),
                                orderProduct.getQuantity()
                        );


                if (!stockResult) {

                    throw new IllegalStateException(
                            "恢复商品库存失败，商品id："
                                    + orderProduct.getProductId()
                    );
                }
            }
        }


        // 删除订单商品
        boolean detailResult =
                orderProductMapper.deleteByOrderId(id);


        if (!detailResult) {

            throw new IllegalStateException(
                    "删除订单商品失败"
            );
        }


        // 删除订单
        return orderMapper.deleteOrderById(id);
    }
}