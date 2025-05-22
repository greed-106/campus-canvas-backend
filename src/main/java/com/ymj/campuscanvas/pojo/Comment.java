package com.ymj.campuscanvas.pojo;

import com.ymj.campuscanvas.pojo.DTO.GetCommentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private LocalDateTime createdTime;

    public GetCommentResponse toGetCommentResponse() {
        return new GetCommentResponse(
                this.id,
                this.userId,
                null, // userName will be set in the service layer
                null, // userAvatar will be set in the service layer
                this.postId,
                this.content,
                this.createdTime,
                0 // likes will be set in the service layer
        );
    }
}
