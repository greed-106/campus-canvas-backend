package com.ymj.campuscanvas.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FollowMapper {
    /**
     * 添加关注
     * @param followerId 粉丝ID
     * @param followeeId 被关注者ID
     */
    @Insert("INSERT INTO follow (follower_id, followee_id) VALUES (#{followerId}, #{followeeId})")
    void insertFollow(@Param("followerId") Long followerId, @Param("followeeId")Long followeeId);
    // 取消关注
    @Insert("DELETE FROM follow WHERE follower_id = #{followerId} AND followee_id = #{followeeId}")
    int deleteFollow(@Param("followerId") Long followerId, @Param("followeeId")Long followeeId);
    // 获取一个人的关注列表
    @Select("SELECT followee_id FROM follow WHERE follower_id = #{userId}")
    List<Long> getFollowingList(@Param("userId") Long userId);
    // 获取一个人的粉丝列表
    @Select("SELECT follower_id FROM follow WHERE followee_id = #{userId}")
    List<Long> getFollowerList(@Param("userId") Long userId);
    // 获取一个人的关注数量
    @Select("SELECT COUNT(*) FROM follow WHERE follower_id = #{userId}")
    int countFollowing(@Param("userId") Long userId); // 获取一个人的关注数量
    // 获取一个人的粉丝数量
    @Select("SELECT COUNT(*) FROM follow WHERE followee_id = #{userId}")
    int countFollowers(@Param("userId") Long userId); // 获取一个人的粉丝数量
}
