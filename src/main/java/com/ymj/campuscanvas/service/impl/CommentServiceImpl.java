package com.ymj.campuscanvas.service.impl;

import com.ymj.campuscanvas.mapper.CommentMapper;
import com.ymj.campuscanvas.pojo.Comment;
import com.ymj.campuscanvas.pojo.DTO.UploadCommentRequest;
import com.ymj.campuscanvas.pojo.DTO.UploadCommentResponse;
import com.ymj.campuscanvas.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Override
    public UploadCommentResponse uploadComment(UploadCommentRequest request) {
        Comment comment = request.toComment();
        try {
            commentMapper.insertComment(comment);
            return new UploadCommentResponse(comment.getId(), comment.getCreatedTime());
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Failed to upload comment: referenced user or post does not exist");
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload comment", e);
        }
    }

    @Override
    public void deleteComment(Long commentId) {
        int affectedRows = commentMapper.deleteComment(commentId);
        if (affectedRows == 0) {
            throw new IllegalArgumentException("Failed to delete comment: comment does not exist");
        }
    }
}
