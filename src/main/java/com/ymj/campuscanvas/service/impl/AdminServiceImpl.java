package com.ymj.campuscanvas.service.impl;

import com.ymj.campuscanvas.exception.NotFoundException;
import com.ymj.campuscanvas.mapper.AdminMapper;
import com.ymj.campuscanvas.mapper.UserMapper;
import com.ymj.campuscanvas.pojo.Admin;
import com.ymj.campuscanvas.pojo.DTO.AdminLoginRequest;
import com.ymj.campuscanvas.pojo.DTO.AdminLoginResponse;
import com.ymj.campuscanvas.pojo.User;
import com.ymj.campuscanvas.service.AdminService;
import com.ymj.campuscanvas.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    
    @Autowired
    private AdminMapper adminMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public AdminLoginResponse adminLogin(AdminLoginRequest request) {
        log.info("Admin login attempt: username={}", request.getUsername());
        
        // 1. 验证用户名和密码
        User user = userMapper.selectUserByUsername(request.getUsername());
        if (user == null) {
            throw new NotFoundException("用户不存在");
        }
        
        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("密码错误");
        }
        
        // 2. 检查用户状态
        if (user.getStatus() != User.UserStatus.ACTIVE) {
            throw new IllegalArgumentException("用户账号已被禁用");
        }
        
        // 3. 检查是否为管理员
        Admin admin = adminMapper.selectAdminByUserId(user.getId());
        if (admin == null) {
            throw new IllegalArgumentException("该用户不是管理员");
        }
        
        // 4. 检查管理员状态
        if (!admin.getIsActive()) {
            throw new IllegalArgumentException("管理员账号已被禁用");
        }
        
        String token = JwtUtil.generateToken(admin.getUserId());
        
        // 6. 构建响应
        AdminLoginResponse response = new AdminLoginResponse(
            token,
            admin.getId(),
            user.getId(),
            user.getUsername(),
            admin.getRole(),
            admin.getIsActive()
        );
        
        log.info("Admin login successful: adminId={}, userId={}, role={}", 
                admin.getId(), user.getId(), admin.getRole());
        
        return response;
    }
    
    @Override
    public Admin getAdminByUserId(Long userId) {
        return adminMapper.selectAdminByUserId(userId);
    }
    
    @Override
    public boolean isAdmin(Long userId) {
        Admin admin = adminMapper.selectAdminByUserId(userId);
        return admin != null && admin.getIsActive();
    }
    
    @Override
    public boolean isSuperAdmin(Long userId) {
        Admin admin = adminMapper.selectAdminByUserId(userId);
        return admin != null && admin.getIsActive() && admin.getRole() == Admin.AdminRole.SUPER_ADMIN;
    }
}