package com.ymj.campuscanvas.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataOverviewResponse {
    private Long totalUsers;
    private Long totalPosts;
    private List<DailyUserCount> dailyUserCounts;
    private List<DailyPostCount> dailyPostCounts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DailyUserCount {
        private String date; // 格式: YYYY-MM-DD
        private Long count;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DailyPostCount {
        private String date; // 格式: YYYY-MM-DD
        private Long count;
    }
}