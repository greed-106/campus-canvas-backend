package com.ymj.campuscanvas.pojo.DTO;

import com.ymj.campuscanvas.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBriefWithStatusResponse {
    private Long id;
    private String username;
    private String avatarUrl;
    private User.UserStatus status;
    private Boolean isBanned;
    
    public UserBriefWithStatusResponse(Long id, String username, String avatarUrl, User.UserStatus status) {
        this.id = id;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.status = status;
        this.isBanned = (status == User.UserStatus.DISABLED);
    }
}