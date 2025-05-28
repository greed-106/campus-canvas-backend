package com.ymj.campuscanvas.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFollowCountsResponse {
    private int followingCount; // 关注数量
    private int followerCount;   // 粉丝数量
}
