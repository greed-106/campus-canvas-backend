package com.ymj.campuscanvas.service;

import com.github.pagehelper.Page;
import com.ymj.campuscanvas.pojo.Post;

import java.util.List;
import java.util.Map;

public interface PostService {
    // TODO: 将方法迁移到PostCompositeService中
    void insertPost(Post post, List<Long> tagIds);

    Page<Post> selectPostsByUserId(
            Long userId, int pageNum, int pageSize, String sortBy, String sortOrder
    );

    Page<Post> selectPostsByKeyword(String keyword, int pageNum, int pageSize, String sortBy, String sortOrder);
    Page<Post> selectHotPosts(int pageNum, int pageSize, int days);
    Post selectPostByPostId(Long postId);
    Map<Long, Post> selectPostsByPostIds(List<Long> postIds);
    void updatePostViewCount(Long postId, int increment);
    // TODO: 将方法迁移到PostCompositeService中
    void updatePost(Post post, List<Long> tagIds);
}
