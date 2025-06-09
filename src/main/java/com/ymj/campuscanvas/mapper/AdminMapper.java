package com.ymj.campuscanvas.mapper;

import com.ymj.campuscanvas.pojo.Admin;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminMapper {
    
    @Select("SELECT * FROM admin WHERE id = #{id}")
    Admin selectAdminById(Long id);
    
    @Select("SELECT * FROM admin WHERE user_id = #{userId}")
    Admin selectAdminByUserId(Long userId);
    
    @Select("SELECT a.* FROM admin a " +
            "INNER JOIN user u ON a.user_id = u.id " +
            "WHERE u.username = #{username} AND a.is_active = 1")
    Admin selectAdminByUsername(String username);
    
    @Insert("INSERT INTO admin(user_id, role, is_active, created_at, updated_at) " +
            "VALUES(#{userId}, #{role}, #{isActive}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertAdmin(Admin admin);
    
    @Update("UPDATE admin SET role = #{role}, is_active = #{isActive}, updated_at = #{updatedAt} " +
            "WHERE id = #{id}")
    void updateAdmin(Admin admin);
    
    @Update("UPDATE admin SET is_active = #{isActive}, updated_at = NOW() WHERE id = #{id}")
    void updateAdminStatus(@Param("id") Long id, @Param("isActive") Boolean isActive);
}