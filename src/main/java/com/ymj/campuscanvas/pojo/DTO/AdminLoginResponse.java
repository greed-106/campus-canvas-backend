package com.ymj.campuscanvas.pojo.DTO;

import com.ymj.campuscanvas.pojo.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginResponse {
    private String token;
    private Long adminId;
    private Long userId;
    private String username;
    private Admin.AdminRole role;
    private Boolean isActive;
}