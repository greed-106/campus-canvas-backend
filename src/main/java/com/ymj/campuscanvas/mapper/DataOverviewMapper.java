package com.ymj.campuscanvas.mapper;

import com.ymj.campuscanvas.pojo.DTO.DataOverviewResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DataOverviewMapper {
    
    @Select("SELECT COUNT(*) FROM user")
    Long getTotalUsers();
    
    @Select("SELECT COUNT(*) FROM post")
    Long getTotalPosts();
    
    @Select("SELECT DATE(created_time) as date, COUNT(*) as count " +
            "FROM user " +
            "WHERE DATE(created_time) BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY DATE(created_time) " +
            "ORDER BY DATE(created_time)")
    List<DataOverviewResponse.DailyUserCount> getDailyUserCounts(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Select("SELECT DATE(created_time) as date, COUNT(*) as count " +
            "FROM post " +
            "WHERE DATE(created_time) BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY DATE(created_time) " +
            "ORDER BY DATE(created_time)")
    List<DataOverviewResponse.DailyPostCount> getDailyPostCounts(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}