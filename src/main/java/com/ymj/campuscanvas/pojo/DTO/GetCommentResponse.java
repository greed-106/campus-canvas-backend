package com.ymj.campuscanvas.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentResponse {
    private Long id;
    private Long userId;
    private String username;
    private String avatarUrl;
    private Long postId;
    private String content;
    private LocalDateTime createdTime;
    private Integer likes;

    public void setUserBrief(UserBriefResponse userBrief) {
        this.userId = userBrief.getId();
        this.username = userBrief.getUsername();
        this.avatarUrl = userBrief.getAvatarUrl();
    }
}
