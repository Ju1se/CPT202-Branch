package com.admin.modelTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.admin.management.model.UserActivity;

public class UserActivityTest {
     @Test
    void testUserActivityCreation() {
        // Arrange
        UserActivity userActivity = new UserActivity();
        Long userId = 1L;
        String activityType = "VIEW_ARTICLE";
        LocalDateTime now = LocalDateTime.now();
        String deviceType = "MOBILE";
        String readingMode = "NIGHT";
        Long contentId = 100L;
        Integer timeSpentSeconds = 300;
        String userAgent = "Mozilla/5.0";
        String ipAddress = "192.168.1.1";

        // Act
        userActivity.setUserId(userId);
        userActivity.setActivityType(activityType);
        userActivity.setTimestamp(now);
        userActivity.setDeviceType(deviceType);
        userActivity.setReadingMode(readingMode);
        userActivity.setContentId(contentId);
        userActivity.setTimeSpentSeconds(timeSpentSeconds);
        userActivity.setUserAgent(userAgent);
        userActivity.setIpAddress(ipAddress);

        // Assert
        assertEquals(userId, userActivity.getUserId());
        assertEquals(activityType, userActivity.getActivityType());
        assertEquals(now, userActivity.getTimestamp());
        assertEquals(deviceType, userActivity.getDeviceType());
        assertEquals(readingMode, userActivity.getReadingMode());
        assertEquals(contentId, userActivity.getContentId());
        assertEquals(timeSpentSeconds, userActivity.getTimeSpentSeconds());
        assertEquals(userAgent, userActivity.getUserAgent());
        assertEquals(ipAddress, userActivity.getIpAddress());
    }

    @Test
    void testUserActivityEquality() {
        // Arrange
        UserActivity activity1 = new UserActivity();
        activity1.setId(1L);
        activity1.setUserId(101L);
        activity1.setActivityType("LOGIN");

        UserActivity activity2 = new UserActivity();
        activity2.setId(1L);
        activity2.setUserId(101L);
        activity2.setActivityType("LOGIN");

        UserActivity activity3 = new UserActivity();
        activity3.setId(2L);
        activity3.setUserId(101L);
        activity3.setActivityType("LOGIN");

        // Assert
        assertEquals(activity1, activity2, "Activities with same ID should be equal");
        assertNotEquals(activity1, activity3, "Activities with different IDs should not be equal");
    }

    @Test
    void testUserActivityHashCode() {
        // Arrange
        UserActivity activity1 = new UserActivity();
        activity1.setId(1L);

        UserActivity activity2 = new UserActivity();
        activity2.setId(1L);

        // Assert
        assertEquals(activity1.hashCode(), activity2.hashCode(), 
                "Hash codes should be equal for equal objects");
    }
}
