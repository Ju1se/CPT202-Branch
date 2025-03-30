package com.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.admin.management.controller.ReportController;
import com.admin.management.dto.TimeRange;
import com.admin.management.dto.UserActivityReportDto;
import com.admin.management.service.ReportService;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {
    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    private UserActivityReportDto mockReportDto;
    private LocalDate testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDate.of(2023, 5, 15);
        
        mockReportDto = new UserActivityReportDto();
        mockReportDto.setTimeRange("DAY");
        mockReportDto.setStartDate(testDate.atStartOfDay());
        mockReportDto.setEndDate(testDate.atTime(23, 59, 59));
        mockReportDto.setTotalActivities(150);
        
        Map<String, Long> readingModeDistribution = new HashMap<>();
        readingModeDistribution.put("DAY", 80L);
        readingModeDistribution.put("NIGHT", 50L);
        readingModeDistribution.put("CUSTOM", 20L);
        mockReportDto.setReadingModeDistribution(readingModeDistribution);
        
        Map<String, Long> activityTypeDistribution = new HashMap<>();
        activityTypeDistribution.put("LOGIN", 30L);
        activityTypeDistribution.put("VIEW_ARTICLE", 60L);
        activityTypeDistribution.put("COMMENT", 40L);
        activityTypeDistribution.put("LOGOUT", 20L);
        mockReportDto.setActivityTypeDistribution(activityTypeDistribution);
        
        Map<String, Long> dailyTrend = new HashMap<>();
        dailyTrend.put("2023-05-15", 150L);
        mockReportDto.setDailyTrend(dailyTrend);
        
        mockReportDto.setAverageReadingTimeSeconds(240.5);
    }

    @Test
    void testGetUserActivityReport() {
        // Arrange
        when(reportService.generateUserActivityReport(any(TimeRange.class), any(LocalDate.class))).thenReturn(mockReportDto);

        // Act
        UserActivityReportDto result = reportController.getUserActivityReport(TimeRange.DAILY, testDate);
        
        // Assert
        assertNotNull(result);
        assertEquals(mockReportDto.getTimeRange(), result.getTimeRange());
        assertEquals(mockReportDto.getStartDate(), result.getStartDate());
        assertEquals(mockReportDto.getEndDate(), result.getEndDate());
        assertEquals(mockReportDto.getTotalActivities(), result.getTotalActivities());
        assertEquals(mockReportDto.getReadingModeDistribution(), result.getReadingModeDistribution());
        assertEquals(mockReportDto.getActivityTypeDistribution(), result.getActivityTypeDistribution());
        assertEquals(mockReportDto.getDailyTrend(), result.getDailyTrend());
        assertEquals(mockReportDto.getAverageReadingTimeSeconds(), result.getAverageReadingTimeSeconds());
        
        // Verify service call
        verify(reportService).generateUserActivityReport(TimeRange.DAILY, testDate);
    }

    @Test
    void testExportUserActivityReport() throws IOException {
        // Arrange
        byte[] mockExcelBytes = "Mock Excel Data".getBytes();
        ByteArrayInputStream mockExcelStream = new ByteArrayInputStream(mockExcelBytes);
        
        when(reportService.exportReportToExcel(any(TimeRange.class), any(LocalDate.class))).thenReturn(mockExcelStream);
        
        // Act
        ResponseEntity<InputStreamResource> response = reportController.exportUserActivityReport(TimeRange.DAILY, testDate);
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        // Check headers
        org.springframework.http.HttpHeaders headers = response.getHeaders();
        assertTrue(headers.containsKey("Content-Disposition"));
        String contentDisposition = headers.getFirst("Content-Disposition");
        assertTrue(contentDisposition.contains("attachment"));
        assertTrue(contentDisposition.contains("user_activity_report_DAILY_2023-05-15.xlsx"));
        
        // Check content type
        assertEquals(MediaType.APPLICATION_OCTET_STREAM, response.getHeaders().getContentType());
        
        // Check response body
        InputStreamResource body = response.getBody();
        assertNotNull(body);
        
        // Verify service call
        verify(reportService).exportReportToExcel(TimeRange.DAILY, testDate);
    }

    @Test
    void testExportUserActivityReportWithIOException() throws IOException {
        when(reportService.exportReportToExcel(any(TimeRange.class), any(LocalDate.class))).thenThrow(new IOException("Test IO exception"));
        Exception exception = assertThrows(IOException.class, () -> {reportController.exportUserActivityReport(TimeRange.DAILY, testDate);});
        assertEquals("Test IO exception", exception.getMessage());
        verify(reportService).exportReportToExcel(TimeRange.DAILY, testDate);}
}
