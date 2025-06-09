package com.ymj.campuscanvas.service;

import com.ymj.campuscanvas.pojo.DTO.DataOverviewResponse;

import java.time.LocalDate;

public interface DataOverviewService {
    /**
     * 获取数据总览信息
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 数据总览响应
     */
    DataOverviewResponse getDataOverview(LocalDate startDate, LocalDate endDate);
}