package com.ymj.campuscanvas.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ymj.campuscanvas.exception.NotFoundException;
import com.ymj.campuscanvas.mapper.TagMapper;
import com.ymj.campuscanvas.pojo.Tag;
import com.ymj.campuscanvas.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TagServiceImpl implements TagService {
    @Autowired
    TagMapper tagMapper;

    @Override
    public void insertTag(Tag tag) {
        // 直接插入，如果已经存在则不插入
        try {
            tagMapper.insertTag(tag);
        } catch (DuplicateKeyException e) {
            // ignore
            log.info("Tag already exists: {}", tag.getName());
            Tag duplicationTag = tagMapper.selectTagByTagName(tag.getName());
            tag.setId(duplicationTag.getId());
            tag.setName(duplicationTag.getName());
            tag.setViewCount(duplicationTag.getViewCount());
            tag.setCreatedTime(duplicationTag.getCreatedTime());
        }
    }

    @Override
    public PageInfo<Tag> selectTagsByKeyword(String tagName, int pageNum, int pageSize) {
        // 会在下一次Mapper查询时动态的修改SQL语句，实现分页
        PageHelper.startPage(pageNum, pageSize);
        List<Tag> tags = tagMapper.selectTagsByKeyword(tagName);
        return new PageInfo<>(tags);
    }

    @Override
    public void updateTagViewCount(Long tagId, int increment) {
        tagMapper.updateTagViewCount(tagId, increment);
    }

    @Override
    public void checkTagExist(List<Long> tagIds) {
        // 查询存在的id
        List<Long> existTagIds = tagMapper.selectExistingTagIds(tagIds);
        // 比对列表
        List<Long> notExistTagIds = new ArrayList<>();
        for (Long tagId : tagIds) {
            if (!existTagIds.contains(tagId)) {
                notExistTagIds.add(tagId);
            }
        }
        if (!notExistTagIds.isEmpty()) {
            throw new NotFoundException("Tag not exist: " + notExistTagIds);
        }
    }
}
