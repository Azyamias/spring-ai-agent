package org.cjj.saaassistant.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer orderId;
    private Integer userId; // 下单用户id
    private Integer totalPrice; // 订单总价
    private String orderStatus; // 支付状态
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    private List<Integer> productIds;
}
