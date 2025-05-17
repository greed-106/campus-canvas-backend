package com.ymj.campuscanvas.service;

import com.github.pagehelper.Page;
import com.ymj.campuscanvas.pojo.Post;

public interface TopicService {
    Page<Post> selectPostsByTagId(
            Long tagId, int pageNum, int pageSize, String sortBy, String sortOrder
    );
}
