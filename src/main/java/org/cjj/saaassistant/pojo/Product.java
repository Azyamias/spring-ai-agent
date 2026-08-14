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
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer stock; // 库存数量
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
}
