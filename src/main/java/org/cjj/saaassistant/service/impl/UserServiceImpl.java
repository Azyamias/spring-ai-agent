package org.cjj.saaassistant.service.impl;

import org.cjj.saaassistant.mapper.UserMapper;
import org.cjj.saaassistant.pojo.User;
import org.cjj.saaassistant.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserById(Integer id) {
        if (id == null) {
            return null;
        }
        return userMapper.selectUserById(id);
    }

    @Override
    @Transactional
    public boolean createUser(User user) {
        if (user == null || user.getUserName() == null || user.getPassword() == null) {
            return false;
        }
        return userMapper.createUser(user);
    }

    @Override
    @Transactional
    public boolean updateUser(User user) {
        if (user == null || user.getUserId() == null) {
            return false;
        }
        return userMapper.updateUser(user);
    }
}
