package org.cjj.saaassistant.pojo;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
}
