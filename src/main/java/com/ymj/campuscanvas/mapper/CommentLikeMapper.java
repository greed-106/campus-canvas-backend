package com.ymj.campuscanvas.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentLikeMapper {

    List<Integer> getLikeCountsByCommentIds(List<Long> commentIds);
}
