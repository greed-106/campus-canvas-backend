package com.ymj.campuscanvas.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ymj.campuscanvas.assembler.CommentAssembler;
import com.ymj.campuscanvas.pojo.Comment;
import com.ymj.campuscanvas.pojo.DTO.GetCommentResponse;
import com.ymj.campuscanvas.pojo.DTO.UserBriefResponse;
import com.ymj.campuscanvas.service.CommentCompositeService;
import com.ymj.campuscanvas.service.CommentService;
import com.ymj.campuscanvas.service.LikeService;
import com.ymj.campuscanvas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentCompositeServiceImpl implements CommentCompositeService {

    @Autowired
    CommentService commentService;
    @Autowired
    LikeService likeService;
    @Autowired
    UserService userService;
    @Autowired
    CommentAssembler commentAssembler;

    @Override
    public PageInfo<GetCommentResponse> selectCommentsByPostId(Long postId, int pageNum, int pageSize) {
        Page<Comment> comments = commentService.getCommentsByPostId(postId, pageNum, pageSize);
        List<Long> userIds = comments.stream()
                .map(Comment::getUserId).collect(Collectors.toList());
        Map<Long, UserBriefResponse> userBriefs = userService.getUserBriefProfilesByIds(userIds);
        List<Long> commentIds = comments.stream()
                .map(Comment::getId).collect(Collectors.toList());
        Map<Long, Integer> likeCounts = likeService.getLikeCountsByCommentIds(commentIds);

        Page<GetCommentResponse> responses = commentAssembler.assembleResponses(comments, userBriefs, likeCounts);
        // 对responses进行按照创建时间进行排序
        responses.sort((o1, o2) -> o2.getCreatedTime().compareTo(o1.getCreatedTime()));
        return new PageInfo<>(responses);
    }
}
