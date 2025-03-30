package com.admin.management.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_activity")
public class UserActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String activityType;
    private LocalDateTime timestamp;
    private String deviceType;
    private String readingMode;
    private Long contentId;
    private Integer timeSpentSeconds;
    private String userAgent;
    private String ipAddress;

    public UserActivity() {
    }

    public UserActivity(Long id, Long userId, String activityType, LocalDateTime timestamp, String deviceType, String readingMode, Long contentId, Integer timeSpentSeconds, String userAgent, String ipAddress) {
        this.id = id;
        this.userId = userId;
        this.activityType = activityType;
        this.timestamp = timestamp;
        this.deviceType = deviceType;
        this.readingMode = readingMode;
        this.contentId = contentId;
        this.timeSpentSeconds = timeSpentSeconds;
        this.userAgent = userAgent;
        this.ipAddress = ipAddress;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getActivityType() {
        return activityType;
    }
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public String getDeviceType() {
        return deviceType;
    }
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    public String getReadingMode() {
        return readingMode;
    }
    public void setReadingMode(String readingMode) {
        this.readingMode = readingMode;
    }
    public Long getContentId() {
        return contentId;
    }
    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }
    public Integer getTimeSpentSeconds() {
        return timeSpentSeconds;
    }
    public void setTimeSpentSeconds(Integer timeSpentSeconds) {
        this.timeSpentSeconds = timeSpentSeconds;
    }
    public String getUserAgent() {
        return userAgent;
    }
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}

