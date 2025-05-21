package com.ymj.campuscanvas.controller;

import com.ymj.campuscanvas.pojo.DTO.LikeRequest;
import com.ymj.campuscanvas.pojo.Result;
import com.ymj.campuscanvas.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/campus-canvas/api/likes")
public class LikeController {
    @Autowired
    LikeService likeService;

    @PostMapping
    public Result handleLikeRequest(@RequestBody LikeRequest likeRequest) {
        log.info("Received like request: {}", likeRequest);
        likeService.handleLikeRequest(likeRequest);
        return Result.success();
    }
}

