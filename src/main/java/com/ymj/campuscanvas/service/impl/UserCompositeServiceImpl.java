package com.ymj.campuscanvas.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ymj.campuscanvas.assembler.UserAssembler;
import com.ymj.campuscanvas.pojo.DTO.UserBriefResponse;
import com.ymj.campuscanvas.service.LikeService;
import com.ymj.campuscanvas.service.PostService;
import com.ymj.campuscanvas.service.UserCompositeService;
import com.ymj.campuscanvas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserCompositeServiceImpl implements UserCompositeService {
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    LikeService likeService;
    @Autowired
    UserAssembler userAssembler;

    @Override
    public PageInfo<UserBriefResponse> selectUserBriefsByLikedPostId(Long postId, int pageNum, int pageSize) {
        Page<Long> userIds = likeService.getLikedUserIdsByPostId(postId, pageNum, pageSize);
        if (userIds == null || userIds.isEmpty()) {
            return new PageInfo<>();
        }

        Map<Long, UserBriefResponse> userBriefsMap = userService.getUserBriefProfilesByIds(userIds);

        Page<UserBriefResponse> userBriefs = userAssembler.assembleUserBriefResponseWithPage(userIds, userBriefsMap);

        return new PageInfo<>(userBriefs);
    }
}
