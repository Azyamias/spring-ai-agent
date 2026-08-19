package org.cjj.saaassistant.service.impl;

import org.cjj.saaassistant.mapper.UserMapper;
import org.cjj.saaassistant.pojo.User;
import org.cjj.saaassistant.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserByIdOrUsername(Integer id, String name) {
        if (id != null) {
            return userMapper.selectUserById(id);
        } else if (name != null) {
            return userMapper.selectUserByName(name);
        } else {
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    @Transactional
    public boolean createUser(User user) {
        if (user == null || user.getName() == null || user.getPassword() == null || user.getEmail() == null) {
            return false;
        }
        return userMapper.insertUser(user);
    }

    @Override
    @Transactional
    public boolean updateUser(User user) {
        if (user == null || user.getId() == null) {
            return false;
        }
        return userMapper.updateUser(user);
    }

    @Override
    @Transactional
    public boolean deleteUser(Integer id) {
        if (id == null) {
            return false;
        }
        return userMapper.deleteUserById(id);
    }
}
