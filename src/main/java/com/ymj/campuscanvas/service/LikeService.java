package com.ymj.campuscanvas.service;

import com.github.pagehelper.Page;
import com.ymj.campuscanvas.pojo.DTO.LikeRequest;
import com.ymj.campuscanvas.pojo.Like;

import java.util.List;
import java.util.Map;

public interface LikeService {
    // TODO: 有待完善评论部分
    void handleLikeRequest(LikeRequest likeRequest);
    void insertPostLike(Like like);
    void deletePostLike(Like like);
    // TODO: 有待完善评论部分
    int countLikesByTargetId(Long targetId, String type);
    Page<Long> getLikedPostIdsByUserId(Long userId, int pageNum, int pageSize);
    Page<Long> getLikedUserIdsByPostId(Long postId, int pageNum, int pageSize);
    Map<Long, Integer> getLikeCountsByPostIds(List<Long> postIds);
    Map<Long, Integer> getLikeCountsByCommentIds(List<Long> commentIds);
}
