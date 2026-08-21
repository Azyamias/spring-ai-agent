package org.cjj.saaassistant.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {
    private Integer id; // 订单项id
    private Integer orderId; // 关联订单
    private Integer productId; // 关联商品
    private String productName;
    private BigDecimal productPrice;
    private Integer quantity; // 购买数量
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
}
