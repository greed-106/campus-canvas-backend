package com.ymj.campuscanvas.mapper;

import com.ymj.campuscanvas.pojo.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {
    @Insert("INSERT INTO post(title, content, user_id, image_urls) VALUES(#{title}, #{content}, #{userId}, #{imageUrls})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertPost(Post post);
    @Select("SELECT * FROM post WHERE user_id = #{userId}")
    List<Post> selectPostsByUserId(Long userId);
    @Select("SELECT * FROM post WHERE title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%')")
    List<Post> selectPostsByKeyword(String keyword);
    List<Post> selectPostsByPostIds(@Param("postIds") List<Long> postIds);

    void updatePost(Post post);
    @Update("UPDATE post SET view_count = view_count + #{increment} WHERE id = #{postId}")
    void updatePostViewCount(Long postId, int increment);
    @Select("SELECT * FROM post WHERE id = #{postId}")
    Post selectPostById(Long postId);

    List<Post> selectHotPosts(int days);
}
