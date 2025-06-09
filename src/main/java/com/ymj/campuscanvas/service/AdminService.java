package com.ymj.campuscanvas.service;

import com.ymj.campuscanvas.pojo.DTO.AdminLoginRequest;
import com.ymj.campuscanvas.pojo.DTO.AdminLoginResponse;
import com.ymj.campuscanvas.pojo.Admin;

public interface AdminService {
    
    /**
     * 管理员登录
     * @param request 登录请求
     * @return 登录响应
     */
    AdminLoginResponse adminLogin(AdminLoginRequest request);
    
    /**
     * 根据用户ID获取管理员信息
     * @param userId 用户ID
     * @return 管理员信息
     */
    Admin getAdminByUserId(Long userId);
    
    /**
     * 检查用户是否为管理员
     * @param userId 用户ID
     * @return 是否为管理员
     */
    boolean isAdmin(Long userId);
    
    /**
     * 检查用户是否为超级管理员
     * @param userId 用户ID
     * @return 是否为超级管理员
     */
    boolean isSuperAdmin(Long userId);
}