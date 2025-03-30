package com.admin.management.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class UserActivityReportDto {
    private String timeRange;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer totalActivities;
    private Map<String, Long> readingModeDistribution;
    private Map<String, Long> activityTypeDistribution;
    private Map<String, Long> dailyTrend;
    private Double averageReadingTimeSeconds;
}
