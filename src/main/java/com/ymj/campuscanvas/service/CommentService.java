package com.ymj.campuscanvas.service;

import com.ymj.campuscanvas.pojo.DTO.UploadCommentRequest;
import com.ymj.campuscanvas.pojo.DTO.UploadCommentResponse;

public interface CommentService {
    UploadCommentResponse uploadComment(UploadCommentRequest request);
    void deleteComment(Long commentId);

}
