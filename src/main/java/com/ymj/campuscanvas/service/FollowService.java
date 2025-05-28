package com.ymj.campuscanvas.service;

import com.github.pagehelper.Page;
import com.ymj.campuscanvas.pojo.DTO.GetFollowCountsResponse;

public interface FollowService {
    /**
     * 添加关注
     * @param followerId 粉丝ID
     * @param followeeId 被关注者ID
     */
    void insertFollow(Long followerId, Long followeeId);

    /**
     * 取消关注
     * @param followerId 粉丝ID
     * @param followeeId 被关注者ID
     */
    void deleteFollow(Long followerId, Long followeeId);

    /**
     * 获取一个人的关注列表
     *
     * @param userId   用户ID
     * @param pageNum
     * @param pageSize
     * @return 关注列表
     */
    Page<Long> getFollowingList(Long userId, int pageNum, int pageSize);

    /**
     * 获取一个人的粉丝列表
     *
     * @param userId   用户ID
     * @param pageNum
     * @param pageSize
     * @return 粉丝列表
     */
    Page<Long> getFollowerList(Long userId, int pageNum, int pageSize);

    /**
     * 获取一个人的关注数量
     * @param userId 用户ID
     * @return 关注数量
     */
    int countFollowing(Long userId);

    /**
     * 获取一个人的粉丝数量
     * @param userId 用户ID
     * @return 粉丝数量
     */
    int countFollowers(Long userId);

    GetFollowCountsResponse getFollowCounts(Long userId);
}
