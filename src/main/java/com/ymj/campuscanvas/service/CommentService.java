package com.ymj.campuscanvas.service;

import com.github.pagehelper.Page;
import com.ymj.campuscanvas.pojo.Comment;
import com.ymj.campuscanvas.pojo.DTO.UploadCommentRequest;
import com.ymj.campuscanvas.pojo.DTO.UploadCommentResponse;

public interface CommentService {
    UploadCommentResponse uploadComment(UploadCommentRequest request);
    void deleteComment(Long commentId);
    Page<Comment> getCommentsByPostId(Long postId, Integer pageNum, Integer pageSize);
}
