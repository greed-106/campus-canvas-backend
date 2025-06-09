package com.ymj.campuscanvas.pojo.DTO;

import com.ymj.campuscanvas.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatusRequest {
    private Long userId;
    private User.UserStatus status;
    private String reason; // 封禁/解封原因（可选）
}