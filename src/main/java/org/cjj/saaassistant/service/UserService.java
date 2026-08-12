package org.cjj.saaassistant.service;

import org.cjj.saaassistant.pojo.User;

public interface UserService {

    // 通过id查询用户
    User getUserById(Integer id);

    // 创建用户
    boolean createUser(User user);

    // 更新用户信息
    boolean updateUser(User user);
}
