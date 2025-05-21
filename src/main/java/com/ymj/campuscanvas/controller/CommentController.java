package com.ymj.campuscanvas.controller;

import com.ymj.campuscanvas.pojo.DTO.UploadCommentRequest;
import com.ymj.campuscanvas.pojo.DTO.UploadCommentResponse;
import com.ymj.campuscanvas.pojo.Result;
import com.ymj.campuscanvas.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/campus-canvas/api/comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping
    public Result uploadComment(@RequestBody UploadCommentRequest request) {
        // TODO: 需要添加评论审核
        UploadCommentResponse res = commentService.uploadComment(request);
        return Result.success(res);
    }

    @DeleteMapping("/{commentId}")
    public Result deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return Result.success();
    }
}
