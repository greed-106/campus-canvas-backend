package com.ymj.campuscanvas.controller;

import com.ymj.campuscanvas.pojo.DTO.AdminLoginRequest;
import com.ymj.campuscanvas.pojo.DTO.AdminLoginResponse;
import com.ymj.campuscanvas.pojo.Result;
import com.ymj.campuscanvas.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campus-canvas/api/admin")
@Slf4j
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    /**
     * 管理员登录
     * @param request 登录请求
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result adminLogin(@RequestBody AdminLoginRequest request) {
        log.info("Admin login request: username={}", request.getUsername());

        AdminLoginResponse response = adminService.adminLogin(request);
        log.info("Admin login successful: adminId={}", response.getAdminId());
        return Result.success(response);
    }
}