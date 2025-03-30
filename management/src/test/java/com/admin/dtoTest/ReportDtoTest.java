package com.admin.dtoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.admin.management.dto.ReportFilterDto;
import com.admin.management.dto.TimeRange;
import com.admin.management.dto.UserActivityReportDto;

public class ReportDtoTest {
    @Test
    void testReportFilterDto() {
        // Arrange
        ReportFilterDto filterDto = new ReportFilterDto();
        TimeRange range = TimeRange.WEEKLY;
        LocalDate date = LocalDate.now();
        
        // Act
        filterDto.setRange(range);
        filterDto.setDate(date);
        
        // Assert
        assertEquals(range, filterDto.getRange());
        assertEquals(date, filterDto.getDate());
    }
    
    @Test
    void testUserActivityReportDto() {
        // Arrange
        UserActivityReportDto reportDto = new UserActivityReportDto();
        String timeRange = "WEEKLY";
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();
        Integer totalActivities = 150;
        
        Map<String, Long> readingModeDistribution = new HashMap<>();
        readingModeDistribution.put("DAY", 80L);
        readingModeDistribution.put("NIGHT", 50L);
        readingModeDistribution.put("CUSTOM", 20L);
        
        Map<String, Long> activityTypeDistribution = new HashMap<>();
        activityTypeDistribution.put("LOGIN", 30L);
        activityTypeDistribution.put("VIEW_ARTICLE", 60L);
        activityTypeDistribution.put("COMMENT", 40L);
        activityTypeDistribution.put("LOGOUT", 20L);
        
        Map<String, Long> dailyTrend = new HashMap<>();
        dailyTrend.put("2023-01-01", 25L);
        dailyTrend.put("2023-01-02", 30L);
        dailyTrend.put("2023-01-03", 35L);
        
        Double averageReadingTimeSeconds = 240.5;
        
        // Act
        reportDto.setTimeRange(timeRange);
        reportDto.setStartDate(startDate);
        reportDto.setEndDate(endDate);
        reportDto.setTotalActivities(totalActivities);
        reportDto.setReadingModeDistribution(readingModeDistribution);
        reportDto.setActivityTypeDistribution(activityTypeDistribution);
        reportDto.setDailyTrend(dailyTrend);
        reportDto.setAverageReadingTimeSeconds(averageReadingTimeSeconds);
        
        // Assert
        assertEquals(timeRange, reportDto.getTimeRange());
        assertEquals(startDate, reportDto.getStartDate());
        assertEquals(endDate, reportDto.getEndDate());
        assertEquals(totalActivities, reportDto.getTotalActivities());
        assertEquals(readingModeDistribution, reportDto.getReadingModeDistribution());
        assertEquals(activityTypeDistribution, reportDto.getActivityTypeDistribution());
        assertEquals(dailyTrend, reportDto.getDailyTrend());
        assertEquals(averageReadingTimeSeconds, reportDto.getAverageReadingTimeSeconds());
    }
    
    @Test
    void testTimeRangeEnum() {
        // Assert
        assertEquals(5, TimeRange.values().length);
        assertEquals(TimeRange.DAILY, TimeRange.valueOf("DAILY"));
        assertEquals(TimeRange.WEEKLY, TimeRange.valueOf("WEEKLY"));
        assertEquals(TimeRange.MONTHLY, TimeRange.valueOf("MONTHLY"));  
        assertEquals(TimeRange.YEARLY, TimeRange.valueOf("YEARLY"));  
        assertEquals(TimeRange.CUSTOM, TimeRange.valueOf("CUSTOM"));  
    }
}
