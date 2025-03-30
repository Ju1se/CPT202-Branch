package com.admin.management.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.management.dto.TimeRange;
import com.admin.management.dto.UserActivityReportDto;
import com.admin.management.model.UserActivity;
import com.admin.management.repository.UserActivityRepo;

@Service
public class ReportService {

    @Autowired
    private UserActivityRepo userActivityRepository;
    
    public UserActivityReportDto generateUserActivityReport(TimeRange range, LocalDate date) {
        LocalDateTime startDate, endDate;
        
        switch (range) {
            case DAILY:
                startDate = date.atStartOfDay();
                endDate = date.atTime(LocalTime.MAX);
                break;
            case WEEKLY:
                startDate = date.minusDays(date.getDayOfWeek().getValue() - 1).atStartOfDay();
                endDate = startDate.plusDays(6).with(LocalTime.MAX);
                break;
            case MONTHLY:
                startDate = date.withDayOfMonth(1).atStartOfDay();
                endDate = date.withDayOfMonth(date.lengthOfMonth()).atTime(LocalTime.MAX);
                break;
            case YEARLY:
                startDate = date.withDayOfYear(1).atStartOfDay();
                endDate = date.withDayOfYear(date.lengthOfYear()).atTime(LocalTime.MAX);
                break;
            case CUSTOM:
                // Handle custom date range if needed
                throw new IllegalArgumentException("Custom time range requires explicit dates");
            default:
                throw new IllegalArgumentException("Invalid time range");
        }
        
        UserActivityReportDto result = new UserActivityReportDto();
        result.setTimeRange(range.toString());
        result.setStartDate(startDate);
        result.setEndDate(endDate);
        
        // Get activities for the period
        List<UserActivity> activities = userActivityRepository.findByTimestampBetween(startDate, endDate);
        result.setTotalActivities(activities.size());
        
        // Reading mode distribution
        List<Object[]> readingModeStats = userActivityRepository.countByReadingMode(startDate, endDate);
        Map<String, Long> readingModeDistribution = new HashMap<>();
        for (Object[] stat : readingModeStats) {
            readingModeDistribution.put((String) stat[0], (Long) stat[1]);
        }
        result.setReadingModeDistribution(readingModeDistribution);
        
        // Activity type distribution
        List<Object[]> activityTypeStats = userActivityRepository.countByActivityType(startDate, endDate);
        Map<String, Long> activityTypeDistribution = new HashMap<>();
        for (Object[] stat : activityTypeStats) {
            activityTypeDistribution.put((String) stat[0], (Long) stat[1]);
        }
        result.setActivityTypeDistribution(activityTypeDistribution);
        
        // Daily activity trend
        List<Object[]> dailyStats = userActivityRepository.countByDay(startDate, endDate);
        Map<String, Long> dailyTrend = new LinkedHashMap<>(); // maintain insertion order
        for (Object[] stat : dailyStats) {
            dailyTrend.put((String) stat[0], (Long) stat[1]);
        }
        result.setDailyTrend(dailyTrend);
        
        // Average reading time
        Double avgReadingTime = userActivityRepository.getAverageReadingTime(startDate, endDate);
        result.setAverageReadingTimeSeconds(avgReadingTime != null ? avgReadingTime : 0);
        
        return result;
    }
    
    public ByteArrayInputStream exportReportToExcel(TimeRange range, LocalDate date) throws IOException {
        UserActivityReportDto reportData = generateUserActivityReport(range, date);
        
        try (Workbook workbook = new XSSFWorkbook(); 
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            
            // Create Overview sheet
            Sheet overviewSheet = workbook.createSheet("Overview");
            int rowNum = 0;
            
            // Create header row
            Row headerRow = overviewSheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("Report Parameter");
            headerRow.createCell(1).setCellValue("Value");
            
            // Add report metadata
            Row timeRangeRow = overviewSheet.createRow(rowNum++);
            timeRangeRow.createCell(0).setCellValue("Time Range");
            timeRangeRow.createCell(1).setCellValue(reportData.getTimeRange());
            
            Row startDateRow = overviewSheet.createRow(rowNum++);
            startDateRow.createCell(0).setCellValue("Start Date");
            startDateRow.createCell(1).setCellValue(reportData.getStartDate().format(DateTimeFormatter.ISO_DATE_TIME));
            
            Row endDateRow = overviewSheet.createRow(rowNum++);
            endDateRow.createCell(0).setCellValue("End Date");
            endDateRow.createCell(1).setCellValue(reportData.getEndDate().format(DateTimeFormatter.ISO_DATE_TIME));
            
            Row totalActivitiesRow = overviewSheet.createRow(rowNum++);
            totalActivitiesRow.createCell(0).setCellValue("Total Activities");
            totalActivitiesRow.createCell(1).setCellValue(reportData.getTotalActivities());
            
            Row avgReadingTimeRow = overviewSheet.createRow(rowNum++);
            avgReadingTimeRow.createCell(0).setCellValue("Average Reading Time (seconds)");
            avgReadingTimeRow.createCell(1).setCellValue(reportData.getAverageReadingTimeSeconds());
            
            // Create Reading Mode Distribution sheet
            Sheet readingModeSheet = workbook.createSheet("Reading Mode Distribution");
            rowNum = 0;
            
            // Header
            headerRow = readingModeSheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("Reading Mode");
            headerRow.createCell(1).setCellValue("Count");
            
            // Data rows
            Map<String, Long> readingModeDistribution = reportData.getReadingModeDistribution();
            for (Map.Entry<String, Long> entry : readingModeDistribution.entrySet()) {
                Row row = readingModeSheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }
            
            // Create Activity Type Distribution sheet
            Sheet activityTypeSheet = workbook.createSheet("Activity Type Distribution");
            rowNum = 0;
            
            // Header
            headerRow = activityTypeSheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("Activity Type");
            headerRow.createCell(1).setCellValue("Count");
            
            // Data rows
            Map<String, Long> activityTypeDistribution = reportData.getActivityTypeDistribution();
            for (Map.Entry<String, Long> entry : activityTypeDistribution.entrySet()) {
                Row row = activityTypeSheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }
            
            // Create Daily Trend sheet
            Sheet dailyTrendSheet = workbook.createSheet("Daily Activity Trend");
            rowNum = 0;
            
            // Header
            headerRow = dailyTrendSheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("Date");
            headerRow.createCell(1).setCellValue("Activity Count");
            
            // Data rows
            Map<String, Long> dailyTrend = reportData.getDailyTrend();
            for (Map.Entry<String, Long> entry : dailyTrend.entrySet()) {
                Row row = dailyTrendSheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }
            
            // Auto-size columns for all sheets
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                for (int j = 0; j < 2; j++) {
                    sheet.autoSizeColumn(j);
                }
            }
            
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}