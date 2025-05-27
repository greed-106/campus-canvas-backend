package com.ymj.campuscanvas.assembler;

import com.github.pagehelper.Page;
import com.ymj.campuscanvas.pojo.DTO.GetPostResponse;
import com.ymj.campuscanvas.pojo.DTO.UserBriefResponse;
import com.ymj.campuscanvas.pojo.Post;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PostAssembler {


    private Page<GetPostResponse> convertPostsToResponsesWithPage(Page<Post> posts) {
        Page<GetPostResponse> responsePage = new Page<>(posts.getPageNum(), posts.getPageSize());
        responsePage.setTotal(posts.getTotal());
        for (Post post : posts) {
            responsePage.add(post.toGetPostResponse());
        }
        return responsePage;
    }

    public GetPostResponse assemblePostWithUserBrief(Post post, UserBriefResponse profile) {
        if (post == null || profile == null) {
            return new GetPostResponse();
        }
        GetPostResponse response = post.toGetPostResponse();

        response.setUsername(profile.getUsername());
        response.setAvatarUrl(profile.getAvatarUrl());

        return response;
    }

    public Page<GetPostResponse> assemblePagePostsWithUserBriefsAndLikeCounts(Page<Post> posts, Map<Long, UserBriefResponse> userBriefs, Map<Long, Integer> likeConuts) {
        if (posts == null || userBriefs == null) {
            return new Page<>();
        }
        Page<GetPostResponse> responsePage = convertPostsToResponsesWithPage(posts);
        for (GetPostResponse response : responsePage) {
            UserBriefResponse profile = userBriefs.get(response.getUserId());
            if (profile != null) {
                response.setUsername(profile.getUsername());
                response.setAvatarUrl(profile.getAvatarUrl());
            }
            Integer likeCount = likeConuts.get(response.getId());
            if (likeCount != null) {
                response.setLikeCount(likeCount);
            } else {
                response.setLikeCount(0);
            }
        }
        return responsePage;
    }
}
