package com.ymj.campuscanvas.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ymj.campuscanvas.assembler.PostAssembler;
import com.ymj.campuscanvas.pojo.DTO.GetPostResponse;
import com.ymj.campuscanvas.pojo.DTO.UserBriefResponse;
import com.ymj.campuscanvas.pojo.Post;
import com.ymj.campuscanvas.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostCompositeServiceImpl implements PostCompositeService {
    @Autowired
    PostService postService;
    @Autowired
    TagService tagService;
    @Autowired
    TopicService topicService;
    @Autowired
    LikeService likeService;
    @Autowired
    PostAssembler postAssembler;
    @Autowired
    UserService userService;

    private Page<GetPostResponse> appendUserBriefsAndLikeCountsToPosts(Page<Post> posts) {
        if (posts == null || posts.isEmpty()) {
            return new Page<>();
        }
        List<Long> userIds = posts.stream().map(Post::getUserId).collect(Collectors.toList());
        Map<Long, UserBriefResponse> userBriefs = userService.getUserBriefProfilesByIds(userIds);

        List<Long> postIds = posts.stream().map(Post::getId).collect(Collectors.toList());
        Map<Long, Integer> likeCounts = likeService.getLikeCountsByPostIds(postIds);

        return postAssembler.assemblePagePostsWithUserBriefsAndLikeCounts(posts, userBriefs, likeCounts);
    }

    @Override
    public PageInfo<GetPostResponse> selectPostsByTagId(Long tagId, int pageNum, int pageSize, String sortBy, String sortOrder) {

        Page<Post> posts = topicService.selectPostsByTagId(tagId, pageNum, pageSize, sortBy, sortOrder);

        Page<GetPostResponse> responsePage = appendUserBriefsAndLikeCountsToPosts(posts);

        return new PageInfo<>(responsePage);
    }

    @Override
    public PageInfo<GetPostResponse> selectPostsByKeyword(String keyword, int pageNum, int pageSize, String sortBy, String sortOrder) {
        Page<Post> posts = postService.selectPostsByKeyword(keyword, pageNum, pageSize, sortBy, sortOrder);

        Page<GetPostResponse> responsePage = appendUserBriefsAndLikeCountsToPosts(posts);

        return new PageInfo<>(responsePage);
    }

    @Override
    public PageInfo<GetPostResponse> selectPostsByUserId(Long userId, int pageNum, int pageSize, String sortBy, String sortOrder) {
        Page<Post> posts = postService.selectPostsByUserId(userId, pageNum, pageSize, sortBy, sortOrder);

        Page<GetPostResponse> responsePage = appendUserBriefsAndLikeCountsToPosts(posts);

        return new PageInfo<>(responsePage);
    }

    @Override
    public PageInfo<GetPostResponse> selectHotPosts(int pageNum, int pageSize, int days) {
        Page<Post> posts = postService.selectHotPosts(pageNum, pageSize, days);

        Page<GetPostResponse> responsePage = appendUserBriefsAndLikeCountsToPosts(posts);

        return new PageInfo<>(responsePage);
    }

    @Override
    public GetPostResponse selectPostByPostId(Long postId) {
        Post post = postService.selectPostByPostId(postId);
        if (post == null) {
            return new GetPostResponse();
        }
        UserBriefResponse userBrief = userService.getUserBriefProfile(post.getUserId());

        return postAssembler.assemblePostWithUserBrief(post, userBrief);
    }

    @Override
    public PageInfo<GetPostResponse> selectPostsByUserLiked(Long userId, int pageNum, int pageSize) {
        Page<Long> postIds = likeService.getLikedPostIdsByUserId(userId, pageNum, pageSize);

        Page<Post> posts = new Page<>(postIds.getPageNum(), postIds.getPageSize());
        posts.setTotal(postIds.getTotal());
        posts.addAll(new ArrayList<>(postService.selectPostsByPostIds(postIds).values()));

        Page<GetPostResponse> responses = appendUserBriefsAndLikeCountsToPosts(posts);

        return new PageInfo<>(responses);
    }


}
