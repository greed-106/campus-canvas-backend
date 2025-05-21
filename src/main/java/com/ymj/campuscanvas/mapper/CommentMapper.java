package com.ymj.campuscanvas.mapper;

import com.ymj.campuscanvas.pojo.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("INSERT INTO comment(user_id, post_id, content) VALUES(#{userId}, #{postId}, #{content})")
    @SelectKey(
            statement = "SELECT id, created_time FROM comment WHERE id = LAST_INSERT_ID()",
            keyProperty = "id,createdTime",
            resultType = Comment.class,
            before = false
    )
    void insertComment(Comment comment);
    @Delete("DELETE FROM comment WHERE id = #{commentId}")
    int deleteComment(Long commentId);
    @Select("SELECT * FROM comment WHERE id = #{commentId}")
    Comment selectCommentByCommentId(Long commentId);

    List<Comment> selectCommentsByPostId(Long postId);
}
