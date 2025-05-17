package com.ymj.campuscanvas.pojo.DTO;

import com.ymj.campuscanvas.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String bio;
    private String avatarUrl;
    private String code;

    public User toUser(){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setBio(bio);
        user.setAvatarUrl(avatarUrl);
        return user;
    }
}
