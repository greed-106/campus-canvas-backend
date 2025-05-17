package com.ymj.campuscanvas.service;

import com.github.pagehelper.PageInfo;
import com.ymj.campuscanvas.pojo.Tag;

import java.util.List;

public interface TagService {
    void insertTag(String tagName);
    PageInfo<Tag> selectTagsByKeyword(String tagName, int pageNum, int pageSize);
    void updateTagViewCount(Long tagId, int increment);

    void checkTagExist(List<Long> tagIds);
}
