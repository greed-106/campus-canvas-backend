package com.ymj.campuscanvas.pojo.DTO;

import com.ymj.campuscanvas.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserProfileRequest {
    private Long id;
    private String password;
    private String bio;
    private String avatarUrl;

    public User toUser() {
        return new User(id, null, password, null, bio, avatarUrl, null, null);
    }
}
