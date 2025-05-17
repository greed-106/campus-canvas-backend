package com.ymj.campuscanvas.service;

import com.github.pagehelper.PageInfo;
import com.ymj.campuscanvas.pojo.DTO.GetPostResponse;

import java.util.List;

public interface PostCompositeService {
    PageInfo<GetPostResponse> selectPostsByTagId(Long tagId, int pageNum, int pageSize, String sortBy, String sortOrder);
    // TODO: 有待完善
    PageInfo<GetPostResponse> selectPostsByKeyword(String keyword, int pageNum, int pageSize, String sortBy, String sortOrder);
    PageInfo<GetPostResponse> selectPostsByUserId(Long userId, int pageNum, int pageSize, String sortBy, String sortOrder);
    PageInfo<GetPostResponse> selectHotPosts(int pageNum, int pageSize, int days);
    GetPostResponse selectPostByPostId(Long postId);
    PageInfo<GetPostResponse> selectPostsByUserLiked(Long userId, int pageNum, int pageSize);

}
