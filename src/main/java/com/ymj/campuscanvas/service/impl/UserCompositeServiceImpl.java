package com.ymj.campuscanvas.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ymj.campuscanvas.assembler.UserAssembler;
import com.ymj.campuscanvas.pojo.DTO.UserBriefResponse;
import com.ymj.campuscanvas.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserCompositeServiceImpl implements UserCompositeService {
    @Autowired
    FavoriteService favoriteService;
    @Autowired
    UserService userService;
    @Autowired
    LikeService likeService;
    @Autowired
    UserAssembler userAssembler;
    @Autowired
    FollowService followService;

    private Page<UserBriefResponse> appendUserBriefsToUserIds(Page<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return new Page<>();
        }
        Map<Long, UserBriefResponse> userBriefsMap = userService.getUserBriefProfilesByIds(userIds);
        return userAssembler.assembleUserBriefResponseWithPage(userIds, userBriefsMap);
    }

    @Override
    public PageInfo<UserBriefResponse> selectUserBriefsByLikedPostId(Long postId, int pageNum, int pageSize) {
        Page<Long> userIds = likeService.getLikedUserIdsByPostId(postId, pageNum, pageSize);
        return new PageInfo<>(appendUserBriefsToUserIds(userIds));
    }

    @Override
    public PageInfo<UserBriefResponse> selectFollowerBriefsByUserId(Long userId, int pageNum, int pageSize) {
        Page<Long> followerIds = followService.getFollowerList(userId, pageNum, pageSize);
        return new PageInfo<>(appendUserBriefsToUserIds(followerIds));
    }

    @Override
    public PageInfo<UserBriefResponse> selectFollowingBriefsByUserId(Long userId, int pageNum, int pageSize) {
        Page<Long> followingIds = followService.getFollowingList(userId, pageNum, pageSize);
        return new PageInfo<>(appendUserBriefsToUserIds(followingIds));
    }

    @Override
    public PageInfo<UserBriefResponse> selectUserBriefsByFavoritePostId(Long postId, int pageNum, int pageSize) {
        Page<Long> userIds = favoriteService.getFavoriteUserIdsByPostId(postId, pageNum, pageSize);
        return new PageInfo<>(appendUserBriefsToUserIds(userIds));
    }
}
