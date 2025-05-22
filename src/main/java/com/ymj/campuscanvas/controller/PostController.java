package com.ymj.campuscanvas.controller;

import com.github.pagehelper.PageInfo;
import com.ymj.campuscanvas.pojo.DTO.*;
import com.ymj.campuscanvas.pojo.Post;
import com.ymj.campuscanvas.pojo.Result;
import com.ymj.campuscanvas.pojo.Tag;
import com.ymj.campuscanvas.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/campus-canvas/api/posts")
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    TagService tagService;
    @Autowired
    UserService userService;
    @Autowired
    TopicService topicService;
    @Autowired
    LikeService likeService;
    @Autowired
    PostCompositeService postCompositeService;
    @Autowired
    UserCompositeService userCompositeService;
    @Autowired
    CommentCompositeService commentCompositeService;

    @PostMapping
    public Result uploadPost(@RequestBody UploadPostRequest request) {
        log.info("request: {}", request);
        userService.checkUserIdExist(request.getUserId());
        tagService.checkTagExist(request.getTagIds());
        Post post = request.toPost();
        postService.insertPost(post, request.getTagIds());

        return Result.success(new UploadPostResponse(post.getId()));
    }

    @GetMapping("/{postId}")
    public Result getPostByPostId(@PathVariable Long postId) {
        GetPostResponse post = postCompositeService.selectPostByPostId(postId);
        return Result.success(post);
    }

    @GetMapping("/{postId}/tags")
    public Result getTagsByPostId(@PathVariable Long postId) {
        List<Tag> tags = topicService.selectTagsByPostId(postId);
        return Result.success(tags);
    }

    @GetMapping("/hot")
    public Result getHotPosts(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "7") int days
    ) {
        PageInfo<GetPostResponse> posts = postCompositeService.selectHotPosts(pageNum, pageSize, days);
        return Result.success(posts);
    }

    @GetMapping
    public Result getPostsByKeyword(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder
    ) {
        PageInfo<GetPostResponse> posts = postCompositeService.selectPostsByKeyword(keyword, pageNum, pageSize, sortBy, sortOrder);
        return Result.success(posts);
    }

    @GetMapping("/{postId}/liked-users")
    public Result getUserBriefsByLikedPostId(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        PageInfo<UserBriefResponse> userBriefs = userCompositeService.selectUserBriefsByLikedPostId(postId, pageNum, pageSize);
        return Result.success(userBriefs);
    }
    @GetMapping("/{postId}/liked-count")
    public Result getPostLikeCounts(
            @PathVariable Long postId
    ) {
        int likeCount = likeService.countLikesByTargetId(postId, "POST");
        return Result.success(likeCount);
    }
    @GetMapping("/{postId}/comments")
    public Result getCommentsByPostId(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        PageInfo<GetCommentResponse> comments = commentCompositeService.selectCommentsByPostId(postId, pageNum, pageSize);
        return Result.success(comments);
    }
}
