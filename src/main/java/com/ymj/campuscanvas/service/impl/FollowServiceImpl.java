package com.ymj.campuscanvas.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymj.campuscanvas.mapper.FollowMapper;
import com.ymj.campuscanvas.pojo.DTO.GetFollowCountsResponse;
import com.ymj.campuscanvas.pojo.Follow;
import com.ymj.campuscanvas.service.FollowService;
import com.ymj.campuscanvas.utils.SortValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowMapper followMapper;
    @Override
    public void insertFollow(Long followerId, Long followeeId) {
        try{
            followMapper.insertFollow(followerId, followeeId);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("Cannot follow the same user multiple times");
        } catch (Exception e) {
            log.error("Error inserting follow: {}", e.getMessage());
            throw new RuntimeException("Error inserting follow");
        }
    }

    @Override
    public void deleteFollow(Long followerId, Long followeeId) {
        int affectedRows = followMapper.deleteFollow(followerId, followeeId);
        if( affectedRows == 0) {
            throw new RuntimeException("Unable to unfollow, no such follow relationship exists.");
        }
    }

    @Override
    public Page<Long> getFollowingList(Long userId, int pageNum, int pageSize) {
        String orderBy = SortValidator.validateSort(Follow.class, "created_time", "desc");
        PageHelper.startPage(pageNum, pageSize, orderBy);

        Page<Long> followingList = (Page<Long>) followMapper.getFollowingList(userId);

        if (followingList == null || followingList.isEmpty()) {
            return new Page<>();
        }

        return followingList;
    }

    @Override
    public Page<Long> getFollowerList(Long userId, int pageNum, int pageSize) {
        String orderBy = SortValidator.validateSort(Follow.class, "created_time", "desc");
        PageHelper.startPage(pageNum, pageSize, orderBy);

        Page<Long> followerList = (Page<Long>) followMapper.getFollowerList(userId);

        if (followerList == null || followerList.isEmpty()) {
            return new Page<>();
        }

        return followerList;
    }

    @Override
    public int countFollowing(Long userId) {
        return followMapper.countFollowing(userId);
    }

    @Override
    public int countFollowers(Long userId) {
        return followMapper.countFollowers(userId);
    }

    @Override
    public GetFollowCountsResponse getFollowCounts(Long userId) {
        int followingCount = countFollowing(userId);
        int followersCount = countFollowers(userId);
        return new GetFollowCountsResponse(followingCount, followersCount);
    }
}
