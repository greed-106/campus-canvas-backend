package com.ymj.campuscanvas.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private Long id;
    private Long userId;
    private AdminRole role = AdminRole.MODERATOR;
    private Boolean isActive = true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public enum AdminRole {
        SUPER_ADMIN, MODERATOR
    }
}