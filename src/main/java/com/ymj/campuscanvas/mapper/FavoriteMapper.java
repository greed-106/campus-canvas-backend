package com.ymj.campuscanvas.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FavoriteMapper {
    @Insert("INSERT INTO favorite(user_id, post_id) VALUES(#{userId}, #{postId})")
    void insertFavorite(@Param("userId") Long userId, @Param("postId") Long postId);
    @Insert("DELETE FROM favorite WHERE user_id = #{userId} AND post_id = #{postId}")
    int deleteFavorite(@Param("userId") Long userId, @Param("postId") Long postId);
    @Select("SELECT COUNT(*) FROM favorite WHERE post_id = #{postId}")
    int selectFavoriteCountByPostId(Long postId);
    @Select("SELECT COUNT(*) FROM favorite WHERE user_id = #{userId}")
    int selectFavoriteCountByUserId(Long userId);
    @Select("SELECT post_id FROM favorite WHERE user_id = #{userId}")
    List<Long> selectFavoritePostIdsByUserId(Long userId);
    @Select("SELECT user_id FROM favorite WHERE post_id = #{postId}")
    List<Long> selectFavoriteUserIdsByPostId(Long postId);
}
