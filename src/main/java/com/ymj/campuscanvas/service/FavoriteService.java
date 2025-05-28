package com.ymj.campuscanvas.service;

import com.github.pagehelper.Page;

import java.util.List;

public interface FavoriteService {
    /**
     * 添加收藏
     *
     * @param userId 用户ID
     * @param postId 帖子ID
     */
    void addFavorite(Long userId, Long postId);

    /**
     * 取消收藏
     *
     * @param userId 用户ID
     * @param postId 帖子ID
     */
    void removeFavorite(Long userId, Long postId);

    /**
     * 获取帖子收藏数量
     *
     * @param postId 帖子ID
     * @return 收藏数量
     */
    int getFavoriteCountByPostId(Long postId);

    /**
     * 获取用户收藏数量
     *
     * @param userId 用户ID
     * @return 收藏数量
     */
    int getFavoriteCountByUserId(Long userId);

    /**
     * 获取用户收藏的帖子ID列表
     *
     * @param userId 用户ID
     * @return 帖子ID列表
     */
    Page<Long> getFavoritePostIdsByUserId(Long userId, int pageNum, int pageSize);

    /**
     * 获取帖子被哪些用户收藏的用户ID列表
     *
     * @param postId 帖子ID
     * @return 用户ID列表
     */
    Page<Long> getFavoriteUserIdsByPostId(Long postId, int pageNum, int pageSize);
}
