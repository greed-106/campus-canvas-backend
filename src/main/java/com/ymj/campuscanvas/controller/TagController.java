package com.ymj.campuscanvas.controller;

import com.github.pagehelper.PageInfo;
import com.ymj.campuscanvas.pojo.DTO.GetPostResponse;
import com.ymj.campuscanvas.pojo.Result;
import com.ymj.campuscanvas.service.PostCompositeService;
import com.ymj.campuscanvas.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/campus-canvas/api/tags")
public class TagController {
    @Autowired
    PostCompositeService postCompositeService;
    @Autowired
    TagService tagService;

    @GetMapping("/{tagId}/posts")
    public Result getPostsByTagId(
            @PathVariable Long tagId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder
    ) {
        PageInfo<GetPostResponse> posts = postCompositeService.selectPostsByTagId(tagId, pageNum, pageSize, sortBy, sortOrder);
        return Result.success(posts);
    }
}
