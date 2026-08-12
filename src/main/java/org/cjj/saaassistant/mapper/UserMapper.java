package org.cjj.saaassistant.mapper;

import org.apache.ibatis.annotations.*;
import org.cjj.saaassistant.pojo.User;

@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User selectUserById(int id);

    @Insert("insert into user (name, password, email) values (#{name}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean createUser(User user);

    @Update("")
    boolean updateUser(User user);
}
