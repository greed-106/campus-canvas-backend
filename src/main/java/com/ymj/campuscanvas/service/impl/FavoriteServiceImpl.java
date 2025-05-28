package com.ymj.campuscanvas.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymj.campuscanvas.mapper.FavoriteMapper;
import com.ymj.campuscanvas.pojo.Favorite;
import com.ymj.campuscanvas.service.FavoriteService;
import com.ymj.campuscanvas.utils.SortValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteMapper favoriteMapper;

    @Override
    public void addFavorite(Long userId, Long postId) {
        try{
            favoriteMapper.insertFavorite(userId, postId);
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("Favorite already exists");
        } catch (Exception e) {
            throw new RuntimeException("Unable to add favorite: " + e.getMessage());
        }

    }

    @Override
    public void removeFavorite(Long userId, Long postId) {
        int affectedRows = favoriteMapper.deleteFavorite(userId, postId);
        if (affectedRows == 0) {
            throw new RuntimeException("Unable to remove favorite: No such favorite found");
        }
    }

    @Override
    public int getFavoriteCountByPostId(Long postId) {
        return favoriteMapper.selectFavoriteCountByPostId(postId);
    }

    @Override
    public int getFavoriteCountByUserId(Long userId) {
        return favoriteMapper.selectFavoriteCountByUserId(userId);
    }

    @Override
    public Page<Long> getFavoritePostIdsByUserId(Long userId, int pageNum, int pageSize) {
        String orderBy = SortValidator.validateSort(Favorite.class, "created_time", "desc");
        Page<Long> page = PageHelper.startPage(pageNum, pageSize, orderBy);

        List<Long> postIds = favoriteMapper.selectFavoritePostIdsByUserId(userId);
        if (postIds == null || postIds.isEmpty()) {
            return new Page<>();
        }
        return (Page<Long>) postIds;
    }

    @Override
    public Page<Long> getFavoriteUserIdsByPostId(Long postId, int pageNum, int pageSize) {
        String orderBy = SortValidator.validateSort(Favorite.class, "created_time", "desc");
        Page<Long> page = PageHelper.startPage(pageNum, pageSize, orderBy);

        List<Long> userIds = favoriteMapper.selectFavoriteUserIdsByPostId(postId);
        if (userIds == null || userIds.isEmpty()) {
            return new Page<>();
        }

        return (Page<Long>) userIds;
    }
}