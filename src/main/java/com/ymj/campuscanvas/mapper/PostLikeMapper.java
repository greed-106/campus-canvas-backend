package com.ymj.campuscanvas.mapper;

import com.ymj.campuscanvas.pojo.Like;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostLikeMapper {
    @Insert("INSERT INTO `like_post`(user_id, target_id) VALUES(#{userId}, #{targetId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertPostLike(Like like);
    @Select("SELECT COUNT(*) FROM `like_post` WHERE target_id = #{postId}")
    int countPostLikesByPostId(Long postId);
    @Select("SELECT COUNT(*) > 0 FROM `like_post` WHERE user_id = #{userId} AND target_id = #{targetId}")
    boolean isLikedByUser(Like like);
    @Delete("DELETE FROM `like_post` WHERE user_id = #{userId} AND target_id = #{targetId}")
    int deleteLike(Like like);
    @Select("SELECT * FROM `like_post` WHERE user_id = #{userId}")
    List<Like> getLikesByUserId(Long userId);
    @Select("SELECT * FROM `like_post` WHERE target_id = #{postId}")
    List<Like> getLikesByPostId(Long postId);
    List<Integer> getLikeCountsByPostIds(List<Long> postIds);
}
