package com.ymj.campuscanvas.controller;

import com.ymj.campuscanvas.pojo.DTO.FollowRequest;
import com.ymj.campuscanvas.pojo.Result;
import com.ymj.campuscanvas.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/campus-canvas/api/follows")
public class FollowController {
    @Autowired
    FollowService followService;

    // 添加关注
    @PostMapping
    public Result followUser(@RequestBody FollowRequest request) {
        log.info("Follow request: {}", request);
        followService.insertFollow(request.getFollowerId(), request.getFollowingId());
        return Result.success();
    }

    // 取消关注
    @DeleteMapping
    public Result unfollowUser(@RequestBody FollowRequest request) {
        log.info("Unfollow request: {}", request);
        followService.deleteFollow(request.getFollowerId(), request.getFollowingId());
        return Result.success();
    }
}
