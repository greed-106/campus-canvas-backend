package com.ymj.campuscanvas.assembler;

import com.github.pagehelper.Page;
import com.ymj.campuscanvas.pojo.Comment;
import com.ymj.campuscanvas.pojo.DTO.GetCommentResponse;
import com.ymj.campuscanvas.pojo.DTO.UserBriefResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommentAssembler {
    private Page<GetCommentResponse> convertCommentsToResponsesWithPage(Page<Comment> comments){
        Page<GetCommentResponse> responsePage = new Page<>(comments.getPageNum(), comments.getPageSize());
        responsePage.setTotal(comments.getTotal());
        for (Comment comment : comments) {
            responsePage.add(comment.toGetCommentResponse());
        }
        return responsePage;
    }

    public Page<GetCommentResponse> assembleResponses(Page<Comment> comments, Map<Long, UserBriefResponse> userBriefs, Map<Long, Integer> likeCounts) {
        Page<GetCommentResponse> responses = convertCommentsToResponsesWithPage(comments);
        for (GetCommentResponse response : responses) {
            Long userId = response.getUserId();
            UserBriefResponse userBrief = userBriefs.get(userId);
            if (userBrief != null) {
                response.setUserBrief(userBrief);
            }
            Integer likeCount = likeCounts.get(response.getId());
            if (likeCount != null) {
                response.setLikes(likeCount);
            }
        }
        return responses;
    }
}
