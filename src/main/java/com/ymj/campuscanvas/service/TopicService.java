package com.ymj.campuscanvas.service;

import com.github.pagehelper.Page;
import com.ymj.campuscanvas.pojo.Post;
import com.ymj.campuscanvas.pojo.Tag;

import java.util.List;

public interface TopicService {
    Page<Post> selectPostsByTagId(
            Long tagId, int pageNum, int pageSize, String sortBy, String sortOrder
    );
    List<Tag> selectTagsByPostId(Long postId);
}
