package com.ymj.campuscanvas.service;

import com.github.pagehelper.PageInfo;
import com.ymj.campuscanvas.pojo.DTO.UserBriefResponse;

public interface UserCompositeService {
    PageInfo<UserBriefResponse> selectUserBriefsByLikedPostId(
            Long postId, int pageNum, int pageSize
    );
}
