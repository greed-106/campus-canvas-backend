package com.ymj.campuscanvas.pojo.DTO;

import com.ymj.campuscanvas.pojo.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadCommentRequest {
    private Long userId;
    private Long postId;
    private String content;

    public Comment toComment() {
        return new Comment(null, userId, postId, content, null);
    }
}
