package com.ymj.campuscanvas.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymj.campuscanvas.exception.NotFoundException;
import com.ymj.campuscanvas.mapper.TopicMapper;
import com.ymj.campuscanvas.pojo.Post;
import com.ymj.campuscanvas.service.PostService;
import com.ymj.campuscanvas.mapper.PostMapper;
import com.ymj.campuscanvas.utils.SortValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    @Autowired
    PostMapper postMapper;
    @Autowired
    TopicMapper topicMapper;

    @Override
    @Transactional
    public void insertPost(Post post, List<Long> tagIds) {
        postMapper.insertPost(post);
        topicMapper.insertTopic(post.getId(), tagIds);
    }

    @Override
    public Page<Post> selectPostsByUserId(
            Long userId, int pageNum, int pageSize, String sortBy, String sortOrder
    ) {
        String orderBy = SortValidator.validateSort(Post.class, sortBy, sortOrder);

        // 会在下一次Mapper查询时动态的修改SQL语句，实现分页
        PageHelper.startPage(pageNum, pageSize, orderBy);
        Page<Post> posts = (Page<Post>)postMapper.selectPostsByUserId(userId);

        return posts;
    }




    @Override
    public Page<Post> selectPostsByKeyword(String keyword, int pageNum, int pageSize, String sortBy, String sortOrder) {
        String orderBy = SortValidator.validateSort(Post.class, sortBy, sortOrder);

        PageHelper.startPage(pageNum, pageSize, orderBy);
        Page<Post> posts = (Page<Post>) postMapper.selectPostsByKeyword(keyword);

        if (posts == null || posts.isEmpty()) {
            return new Page<>();
        }
        return posts;
    }


    @Override
    public Page<Post> selectHotPosts(int pageNum, int pageSize, int days) {

        PageHelper.startPage(pageNum, pageSize);
        Page<Post> posts = (Page<Post>) postMapper.selectHotPosts(days);
        if (posts == null || posts.isEmpty()) {
            return new Page<>();
        }

        return posts;
    }

    @Override
    public Post selectPostByPostId(Long postId) {
        Post post = postMapper.selectPostById(postId);
        if (post == null) {
            throw new NotFoundException("Post not found");
        }

        return post;
    }

    @Override
    public Map<Long, Post> selectPostsByPostIds(List<Long> postIds) {
        if (postIds == null || postIds.isEmpty()) {
            return Collections.emptyMap();
        }
        // 1. 去重
        Set<Long> uniqueIds = new HashSet<>(postIds);
        List<Post> posts = postMapper.selectPostsByPostIds(new ArrayList<>(uniqueIds));
        // 2. 构建ID到Post的映射
        Map<Long, Post> postMap = posts.stream()
                .collect(Collectors.toMap(Post::getId, post -> post));
        // 3. 判断是否有缺失的ID
        for (Long id : uniqueIds) {
            if (!postMap.containsKey(id)) {
                throw new NotFoundException("Post with ID: " + id + " not found");
            }
        }

        return postMap;
    }

    @Override
    public void updatePostViewCount(Long postId, int increment) {
        postMapper.updatePostViewCount(postId, increment);
    }

    @Override
    @Transactional
    public void updatePost(Post post, List<Long> tagIds) {
        postMapper.updatePost(post);
        // 删除旧的标签
        topicMapper.deleteTopicByPostId(post.getId());
        // 插入新的标签
        topicMapper.insertTopic(post.getId(), tagIds);
    }
}
