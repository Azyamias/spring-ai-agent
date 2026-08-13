package org.cjj.saaassistant.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer productStock; // 库存数量
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
}
