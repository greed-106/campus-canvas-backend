package com.ymj.campuscanvas.mapper;

import com.ymj.campuscanvas.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectUserById(Long id);
    List<User> selectUsersByIds(List<Long> ids);
    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectUserByUsername(String username);
    @Select("SELECT * FROM user WHERE email = #{email}")
    User selectUserByEmail(String email);
    @Insert("INSERT INTO user(username, password, email, bio, avatar_url) VALUES(#{username}, #{password}, #{email}, #{bio}, #{avatarUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertUser(User user);
    void updateUser(User user);
    
    @Update("UPDATE user SET status = #{status} WHERE id = #{userId}")
    void updateUserStatus(@Param("userId") Long userId, @Param("status") User.UserStatus status);
    
    @Select("SELECT * FROM user WHERE username LIKE CONCAT('%', #{username}, '%') AND status = 'ACTIVE' ORDER BY username LIMIT 20")
    List<User> searchUsersByUsername(String username);
    
    @Select("SELECT * FROM user ORDER BY created_time DESC")
    List<User> selectAllUsers();
}
