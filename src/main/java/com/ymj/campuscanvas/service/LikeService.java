package com.ymj.campuscanvas.service;

import com.github.pagehelper.Page;
import com.ymj.campuscanvas.pojo.DTO.LikeRequest;

import java.util.List;

public interface LikeService {
    void insertLike(LikeRequest likeRequest);
    void deleteLike(LikeRequest likeRequest);
    // TODO: 有待完善
    int countLikesByTargetId(Long targetId, String type);
    Page<Long> getLikedPostIdsByUserId(Long userId, int pageNum, int pageSize);
    Page<Long> getLikedUserIdsByPostId(Long postId, int pageNum, int pageSize);
}
