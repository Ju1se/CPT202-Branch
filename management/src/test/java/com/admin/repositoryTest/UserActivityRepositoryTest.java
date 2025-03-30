package com.admin.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.admin.management.model.UserActivity;
import com.admin.management.repository.UserActivityRepo;
import com.admin.management.TestConfiguration;

@DataJpaTest
@ContextConfiguration(classes = TestConfiguration.class)
@ActiveProfiles("test")
public class UserActivityRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserActivityRepo repository;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @BeforeEach
    void setUp() {
        // Set up test data
        startDate = LocalDateTime.now().minusDays(7);
        endDate = LocalDateTime.now();

        // Create and persist multiple user activities
        createAndPersistUserActivity(101L, "LOGIN", startDate.plusHours(1), "DESKTOP", "DAY");
        createAndPersistUserActivity(102L, "VIEW_ARTICLE", startDate.plusHours(2), "MOBILE", "NIGHT");
        createAndPersistUserActivity(101L, "COMMENT", startDate.plusHours(3), "TABLET", "CUSTOM");
        createAndPersistUserActivity(103L, "VIEW_ARTICLE", startDate.plusHours(4), "MOBILE", "NIGHT");
        createAndPersistUserActivity(102L, "LOGOUT", startDate.plusHours(5), "DESKTOP", "DAY");
        
        // Create activity outside date range
        createAndPersistUserActivity(104L, "LOGIN", startDate.minusDays(1), "DESKTOP", "DAY");
    }

    private void createAndPersistUserActivity(Long userId, String activityType, 
                                            LocalDateTime timestamp, String deviceType, 
                                            String readingMode) {
        UserActivity activity = new UserActivity();
        activity.setUserId(userId);
        activity.setActivityType(activityType);
        activity.setTimestamp(timestamp);
        activity.setDeviceType(deviceType);
        activity.setReadingMode(readingMode);
        activity.setTimeSpentSeconds(activityType.equals("VIEW_ARTICLE") ? 300 : 60);
        
        if (activityType.equals("VIEW_ARTICLE")) {
            activity.setContentId(1000L);
        }
        
        entityManager.persist(activity);
    }

    @Test
    void testFindByTimestampBetween() {
        // Act
        List<UserActivity> activities = repository.findByTimestampBetween(startDate, endDate);
        
        // Assert
        assertEquals(5, activities.size(), "Should find 5 activities within date range");
        
        // Verify all activities are within date range
        activities.forEach(activity -> {
            assertTrue(activity.getTimestamp().isAfter(startDate) || activity.getTimestamp().isEqual(startDate));
            assertTrue(activity.getTimestamp().isBefore(endDate) || activity.getTimestamp().isEqual(endDate));
        });
    }

    @Test
    void testCountByReadingMode() {
        // Act
        List<Object[]> results = repository.countByReadingMode(startDate, endDate);
        
        // Assert
        assertEquals(3, results.size(), "Should find 3 different reading modes");
        
        // Convert results to a more testable format
        boolean foundDay = false;
        boolean foundNight = false;
        boolean foundCustom = false;
        
        for (Object[] result : results) {
            String mode = (String) result[0];
            Long count = (Long) result[1];
            
            if ("DAY".equals(mode)) {
                assertEquals(2L, count);
                foundDay = true;
            } else if ("NIGHT".equals(mode)) {
                assertEquals(2L, count);
                foundNight = true;
            } else if ("CUSTOM".equals(mode)) {
                assertEquals(1L, count);
                foundCustom = true;
            }
        }
        
        assertTrue(foundDay && foundNight && foundCustom, "Should find all three reading modes");
    }

    @Test
    void testCountByActivityType() {
        // Act
        List<Object[]> results = repository.countByActivityType(startDate, endDate);
        
        // Assert
        assertEquals(4, results.size(), "Should find 4 different activity types");
        
        // Convert results to a more testable format
        boolean foundLogin = false;
        boolean foundViewArticle = false;
        boolean foundComment = false;
        boolean foundLogout = false;
        
        for (Object[] result : results) {
            String type = (String) result[0];
            Long count = (Long) result[1];
            
            if ("LOGIN".equals(type)) {
                assertEquals(1L, count);
                foundLogin = true;
            } else if ("VIEW_ARTICLE".equals(type)) {
                assertEquals(2L, count);
                foundViewArticle = true;
            } else if ("COMMENT".equals(type)) {
                assertEquals(1L, count);
                foundComment = true;
            } else if ("LOGOUT".equals(type)) {
                assertEquals(1L, count);
                foundLogout = true;
            }
        }
        
        assertTrue(foundLogin && foundViewArticle && foundComment && foundLogout, 
                "Should find all four activity types");
    }

    @Test
    void testGetAverageReadingTime() {
        // Act
        Double avgTime = repository.getAverageReadingTime(startDate, endDate);
        
        // Assert
        assertNotNull(avgTime, "Average reading time should not be null");
        assertEquals(300.0, avgTime, "Average reading time should be 300 seconds");
    }
}
