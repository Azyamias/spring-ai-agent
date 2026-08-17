package org.cjj.saaassistant.service;

import org.cjj.saaassistant.pojo.User;

import java.util.List;

public interface UserService {

    // 通过用户id或用户名查询用户
    User getUserByIdOrUsername(Integer id, String username);

    // 查询所有用户
    List<User> getAllUsers();
}
