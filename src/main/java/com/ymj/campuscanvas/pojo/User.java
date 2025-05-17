package com.ymj.campuscanvas.pojo;

import com.ymj.campuscanvas.pojo.DTO.UserBriefResponse;
import com.ymj.campuscanvas.pojo.DTO.UserProfileResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String bio;
    private String avatarUrl;
    private LocalDateTime createdTime;
    private UserStatus status = UserStatus.ACTIVE;

    public enum UserStatus { ACTIVE, DISABLED, DELETED }

    public UserProfileResponse toUserProfileDTO(){
        return new UserProfileResponse(id, username, email, bio, avatarUrl);
    }

    public UserBriefResponse toUserBriefDTO(){
        return new UserBriefResponse(id, username, avatarUrl);
    }
}