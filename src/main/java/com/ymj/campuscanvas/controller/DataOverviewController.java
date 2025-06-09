package com.ymj.campuscanvas.controller;

import com.ymj.campuscanvas.pojo.DTO.DataOverviewRequest;
import com.ymj.campuscanvas.pojo.DTO.DataOverviewResponse;
import com.ymj.campuscanvas.pojo.Result;
import com.ymj.campuscanvas.service.DataOverviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/campus-canvas/api/overview")
public class DataOverviewController {
    
    @Autowired
    private DataOverviewService dataOverviewService;
    
    /**
     * 获取数据总览
     * @param startDate 开始日期 (格式: yyyy-MM-dd)
     * @param endDate 结束日期 (格式: yyyy-MM-dd)
     * @return 数据总览信息
     */
    @GetMapping
    public Result getDataOverview(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        log.info("Getting data overview for period: {} to {}", startDate, endDate);
        
        // 验证日期参数
        if (startDate.isAfter(endDate)) {
            return Result.failure("开始日期不能晚于结束日期");
        }
        
        DataOverviewResponse response = dataOverviewService.getDataOverview(startDate, endDate);
        return Result.success(response);
    }
}