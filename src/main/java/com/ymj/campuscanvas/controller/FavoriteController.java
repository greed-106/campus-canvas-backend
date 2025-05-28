package com.ymj.campuscanvas.controller;

import com.ymj.campuscanvas.pojo.DTO.FavoriteRequest;
import com.ymj.campuscanvas.pojo.Result;
import com.ymj.campuscanvas.service.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/campus-canvas/api/favorites")
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;

    // 添加收藏
    @PostMapping
    public Result addFavorite(@RequestBody FavoriteRequest request) {
        favoriteService.addFavorite(request.getUserId(), request.getPostId());
        return Result.success();
    }

    // 取消收藏
    @DeleteMapping
    public Result removeFavorite(@RequestBody FavoriteRequest request) {
        favoriteService.removeFavorite(request.getUserId(), request.getPostId());
        return Result.success();
    }
}
