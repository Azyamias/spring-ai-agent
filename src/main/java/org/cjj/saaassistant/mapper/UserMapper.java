package org.cjj.saaassistant.mapper;

import org.apache.ibatis.annotations.*;
import org.cjj.saaassistant.pojo.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from users where id = #{id}")
    User selectUserById(Integer id);

    @Select("select * from users where name = #{name}")
    User selectUserByName(String name);

    @Select("select * from users")
    List<User> selectAllUsers();

    @Insert("insert into users (name, password, email) values (#{name}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean insertUser(User user);

    @Update("update users set name = #{name}, password = #{password}, email = #{email} where id = #{id}")
    boolean updateUser(User user);

    @Delete("delete from users where id = #{id}")
    boolean deleteUserById(Integer id);
}
