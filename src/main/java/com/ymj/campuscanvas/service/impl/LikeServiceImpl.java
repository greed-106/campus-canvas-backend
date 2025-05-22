package com.ymj.campuscanvas.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymj.campuscanvas.mapper.CommentLikeMapper;
import com.ymj.campuscanvas.mapper.PostLikeMapper;
import com.ymj.campuscanvas.pojo.DTO.LikeRequest;
import com.ymj.campuscanvas.pojo.Like;
import com.ymj.campuscanvas.service.LikeService;
import com.ymj.campuscanvas.utils.SortValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LikeServiceImpl implements LikeService {

    @Autowired
    PostLikeMapper postLikeMapper;
    @Autowired
    CommentLikeMapper commentLikeMapper;

    @Override
    public void handleLikeRequest(LikeRequest likeRequest) {
        if(!likeRequest.isComment() && !likeRequest.isPost()) {
            throw new IllegalArgumentException("Invalid like request");
        }
        log.info("Handling like request: {}", likeRequest);

        if(likeRequest.isPost()){
            if(likeRequest.isLike()){
                insertPostLike(likeRequest.toLike());
            }else{
                deletePostLike(likeRequest.toLike());
            }
        }else{
            throw new IllegalArgumentException("Comment like is not supported yet");
        }
    }

    @Override
    public void insertPostLike(Like like) {
        try {
            postLikeMapper.insertPostLike(like);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("Cannot like the same post multiple times");
        } catch (Exception e) {
            log.error("Error inserting like: {}", e.getMessage());
            throw new RuntimeException("Error inserting like");
        }
    }

    @Override
    public void deletePostLike(Like like) {
        int affectedRows = postLikeMapper.deleteLike(like);
        if (affectedRows == 0) {
            throw new IllegalArgumentException("Unable to delete like, like not found");
        }
    }

    @Override
    public int countLikesByTargetId(Long targetId, String type) {
        if(!Like.LikeType.isValidType(type)) {
            throw new IllegalArgumentException("Invalid like type: " + type);
        }

        Like.LikeType likeType = Like.LikeType.valueOf(type.toUpperCase());
        if(likeType == Like.LikeType.POST) {
            return postLikeMapper.countPostLikesByPostId(targetId);
        } else {
            throw new IllegalArgumentException("Comment like count is not supported yet");
        }
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
        Page<Like> likes = (Page<Like>) postLikeMapper.getLikesByUserId(userId);

        return convertLikesToIdsWithPage(likes, Like::getTargetId);
    }

    @Override
    public Page<Long> getLikedUserIdsByPostId(Long postId, int pageNum, int pageSize) {
        // 分页
        String oderBy = SortValidator.validateSort(Like.class, "created_time", "desc");
        PageHelper.startPage(pageNum, pageSize, oderBy);
        Page<Like> likes = (Page<Like>) postLikeMapper.getLikesByPostId(postId);

        return convertLikesToIdsWithPage(likes, Like::getUserId);
    }

    @Override
    public Map<Long, Integer> getLikeCountsByPostIds(List<Long> postIds) {
        if (postIds == null || postIds.isEmpty()) {
            return Collections.emptyMap();
        }

        List<Integer> likeCounts = postLikeMapper.getLikeCountsByPostIds(postIds);
        if (likeCounts.size() != postIds.size()) {
            throw new RuntimeException("Like counts size does not match post IDs size");
        }

        // 对postIds进行从小到大的排序
        List<Long> sortedPostIds = postIds.stream()
                .sorted()
                .collect(Collectors.toList());
        // 构建postIds到likeCounts的映射
        Map<Long, Integer> postIdToLikeCountMap = sortedPostIds.stream()
                .collect(Collectors.toMap(Function.identity(), postId -> likeCounts.get(postIds.indexOf(postId))));
        return postIdToLikeCountMap;
    }

    @Override
    public Map<Long, Integer> getLikeCountsByCommentIds(List<Long> commentIds) {
        if (commentIds == null || commentIds.isEmpty()) {
            return Collections.emptyMap();
        }

        List<Integer> likeCounts = commentLikeMapper.getLikeCountsByCommentIds(commentIds);
        if (likeCounts.size() != commentIds.size()) {
            throw new RuntimeException("Like counts size does not match comment IDs size");
        }

        // 对commentIds进行从小到大的排序
        List<Long> sortedCommentIds = commentIds.stream()
                .sorted()
                .collect(Collectors.toList());
        // 构建commentIds到likeCounts的映射
        Map<Long, Integer> commentIdToLikeCountMap = sortedCommentIds.stream()
                .collect(Collectors.toMap(Function.identity(), commentId -> likeCounts.get(commentIds.indexOf(commentId))));
        return commentIdToLikeCountMap;
    }
}
