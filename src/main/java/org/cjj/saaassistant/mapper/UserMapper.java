package org.cjj.saaassistant.mapper;

import org.apache.ibatis.annotations.*;
import org.cjj.saaassistant.pojo.User;

@Mapper
public interface UserMapper {

    @Select("select * from users where id = #{id}")
    User selectUserById(Integer id);

    @Insert("insert into users (name, password, email) values (#{name}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean insertUser(User user);

    @Update("update users set name = #{name}, password = #{password}, email = #{email} where id = #{id}")
    boolean updateUser(User user);
}
