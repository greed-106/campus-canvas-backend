package com.ymj.campuscanvas.mapper;

import com.ymj.campuscanvas.pojo.Post;
import com.ymj.campuscanvas.pojo.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TopicMapper {
    void insertTopic(@Param("postId") Long postId, @Param("tagIds") List<Long> tagIds);
    @Select("SELECT tag_id FROM topic WHERE post_id = #{postId}")
    List<Long> selectTagIdsByPostId(Long postId);
    @Select("SELECT post_id FROM topic WHERE tag_id = #{tagId}")
    List<Long> selectPostIdsByTagId(Long tagId);
    @Delete("DELETE FROM topic WHERE post_id = #{postId}")
    void deleteTopicByPostId(Long postId);
    // 因为这个接口需要分页，所以不使用二次查询
    List<Post> selectPostsByTagId(Long tagId);
    @Select("SELECT * FROM tag WHERE id IN (SELECT tag_id FROM topic WHERE post_id = #{postId})")
    List<Tag> selectTagsByPostId(@Param("postId") Long postId);
}
