package com.ymj.campuscanvas.assembler;

import com.github.pagehelper.Page;
import com.ymj.campuscanvas.pojo.DTO.UserBriefResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UserAssembler {

    public Page<UserBriefResponse> assembleUserBriefResponseWithPage(
            Page<Long> userIds, Map<Long, UserBriefResponse> userBriefsMap
    ){
        if (userIds == null || userIds.isEmpty()) {
            return new Page<>();
        }

        Page<UserBriefResponse> responsePage = new Page<>(userIds.getPageNum(), userIds.getPageSize());
        responsePage.setTotal(userIds.getTotal());
        List<UserBriefResponse> userBriefs = new ArrayList<>();
        for (Long userId : userIds) {
            UserBriefResponse userBrief = userBriefsMap.get(userId);
            if (userBrief != null) {
                userBriefs.add(userBrief);
            }
        }
        responsePage.addAll(userBriefs);

        return responsePage;
    }
}
