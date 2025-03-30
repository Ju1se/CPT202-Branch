package com.admin.management.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.admin.management.dto.TimeRange;
import com.admin.management.dto.UserActivityReportDto;
import com.admin.management.service.ReportService;


@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/user-activity")
    @ResponseBody
    public UserActivityReportDto getUserActivityReport(
            @RequestParam("range") TimeRange range,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return reportService.generateUserActivityReport(range, date);
    }
    
    @GetMapping("/user-activity/export")
    public ResponseEntity<InputStreamResource> exportUserActivityReport(
            @RequestParam("range") TimeRange range,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws IOException {
        
        ByteArrayInputStream stream = reportService.exportReportToExcel(range, date);
        
        String filename = "user_activity_report_" + range + "_" + date.format(DateTimeFormatter.ISO_DATE) + ".xlsx";
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + filename);
        
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(stream));
    }
}
