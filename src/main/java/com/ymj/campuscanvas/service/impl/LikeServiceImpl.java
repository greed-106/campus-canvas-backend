package com.ymj.campuscanvas.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymj.campuscanvas.mapper.PostLikeMapper;
import com.ymj.campuscanvas.pojo.DTO.LikeRequest;
import com.ymj.campuscanvas.pojo.Like;
import com.ymj.campuscanvas.service.LikeService;
import com.ymj.campuscanvas.utils.SortValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LikeServiceImpl implements LikeService {

    @Autowired
    PostLikeMapper likeMapper;


    @Override
    public void insertLike(LikeRequest likeRequest) {
        likeMapper.insertLike(likeRequest.toLike());
    }

    @Override
    public void deleteLike(LikeRequest likeRequest) {
        likeMapper.deleteLike(likeRequest.toLike());
    }

    @Override
    public int countLikesByTargetId(Long targetId, String type) {
        if(!Like.LikeType.isValidType(type)) {
            throw new IllegalArgumentException("Invalid like type: " + type);
        }

        Like.LikeType likeType = Like.LikeType.valueOf(type.toUpperCase());
        return likeMapper.countLikesByPostId(targetId);
    }

    private Page<Long> convertLikesToIdsWithPage(Page<Like> likes, Function<Like, Long> idExtractor) {
        // 提取likes中的id
        List<Long> ids = likes.stream().map(idExtractor).collect(Collectors.toList());
        Page<Long> idsPage = new Page<>(likes.getPageNum(), likes.getPageSize());
        idsPage.setTotal(likes.getTotal());
        idsPage.addAll(ids);

        return idsPage;
    }

    @Override
    public Page<Long> getLikedPostIdsByUserId(Long userId, int pageNum, int pageSize) {
        // 分页
        String oderBy = SortValidator.validateSort(Like.class, "created_time", "desc");
        PageHelper.startPage(pageNum, pageSize, oderBy);
        Page<Like> likes = (Page<Like>) likeMapper.getLikesByUserId(userId);

        return convertLikesToIdsWithPage(likes, Like::getTargetId);
    }

    @Override
    public Page<Long> getLikedUserIdsByPostId(Long postId, int pageNum, int pageSize) {
        // 分页
        String oderBy = SortValidator.validateSort(Like.class, "created_time", "desc");
        PageHelper.startPage(pageNum, pageSize, oderBy);
        Page<Like> likes = (Page<Like>) likeMapper.getLikesByPostId(postId);

        return convertLikesToIdsWithPage(likes, Like::getUserId);
    }
}
