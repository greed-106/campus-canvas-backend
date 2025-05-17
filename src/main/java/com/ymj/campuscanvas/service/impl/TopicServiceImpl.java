package com.ymj.campuscanvas.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymj.campuscanvas.mapper.TopicMapper;
import com.ymj.campuscanvas.pojo.Post;
import com.ymj.campuscanvas.service.TopicService;
import com.ymj.campuscanvas.utils.SortValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    TopicMapper topicMapper;

    @Override
    public Page<Post> selectPostsByTagId(Long tagId, int pageNum, int pageSize, String sortBy, String sortOrder) {
        String orderBy = SortValidator.validateSort(Post.class, sortBy, sortOrder);
        PageHelper.startPage(pageNum, pageSize, orderBy);

        Page<Post> posts = (Page<Post>) topicMapper.selectPostsByTagId(tagId);

        return posts;
    }
}
