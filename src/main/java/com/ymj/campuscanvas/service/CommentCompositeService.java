package com.ymj.campuscanvas.service;

import com.github.pagehelper.PageInfo;
import com.ymj.campuscanvas.pojo.DTO.GetCommentResponse;

public interface CommentCompositeService {
    PageInfo<GetCommentResponse> selectCommentsByPostId(Long postId, int pageNum, int pageSize);
}
