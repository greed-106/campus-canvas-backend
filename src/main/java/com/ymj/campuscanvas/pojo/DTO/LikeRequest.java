package com.ymj.campuscanvas.pojo.DTO;

import com.ymj.campuscanvas.pojo.Like;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeRequest {
    private Long userId;
    private Long targetId;
    private String type; // "POST" or "COMMENT"
    private boolean isLike; // true for like, false for unlike

    public boolean isPost() {
        return "POST".equalsIgnoreCase(type);
    }

    public boolean isComment() {
        return "COMMENT".equalsIgnoreCase(type);
    }

    public boolean isLike() {
        return isLike;
    }

    public Like toLike() {
        Like.LikeType likeType;
        if (isPost()) {
            likeType = Like.LikeType.POST;
        } else if (isComment()) {
            likeType = Like.LikeType.COMMENT;
        } else {
            throw new IllegalArgumentException("Invalid type: " + type);
        }

        return new Like(null, userId, targetId, likeType, null);
    }
}
