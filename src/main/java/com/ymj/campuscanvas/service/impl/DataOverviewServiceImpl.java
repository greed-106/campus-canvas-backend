package com.ymj.campuscanvas.service.impl;

import com.ymj.campuscanvas.mapper.DataOverviewMapper;
import com.ymj.campuscanvas.pojo.DTO.DataOverviewResponse;
import com.ymj.campuscanvas.service.DataOverviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class DataOverviewServiceImpl implements DataOverviewService {
    
    @Autowired
    private DataOverviewMapper dataOverviewMapper;
    
    @Override
    public DataOverviewResponse getDataOverview(LocalDate startDate, LocalDate endDate) {
        log.info("Getting data overview for period: {} to {}", startDate, endDate);
        
        // 获取用户总数
        Long totalUsers = dataOverviewMapper.getTotalUsers();
        
        // 获取帖子总数
        Long totalPosts = dataOverviewMapper.getTotalPosts();
        
        // 获取指定时间段内每日新增用户数
        List<DataOverviewResponse.DailyUserCount> dailyUserCounts = 
            dataOverviewMapper.getDailyUserCounts(startDate, endDate);
        
        // 获取指定时间段内每日新增帖子数
        List<DataOverviewResponse.DailyPostCount> dailyPostCounts = 
            dataOverviewMapper.getDailyPostCounts(startDate, endDate);
        
        DataOverviewResponse response = new DataOverviewResponse(
            totalUsers,
            totalPosts,
            dailyUserCounts,
            dailyPostCounts
        );
        
        log.info("Data overview result: totalUsers={}, totalPosts={}, dailyUserCounts size={}, dailyPostCounts size={}", 
                totalUsers, totalPosts, dailyUserCounts.size(), dailyPostCounts.size());
        
        return response;
    }
}