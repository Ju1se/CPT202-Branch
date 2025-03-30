package com.admin.management.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.admin.management.model.UserActivity;

@Repository
public interface UserActivityRepo extends JpaRepository<UserActivity, Long>{
    List<UserActivity> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT ua.readingMode, COUNT(ua) FROM UserActivity ua WHERE ua.timestamp BETWEEN :startDate AND :endDate GROUP BY ua.readingMode")
    List<Object[]> countByReadingMode(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ua.activityType, COUNT(ua) FROM UserActivity ua WHERE ua.timestamp BETWEEN :startDate AND :endDate GROUP BY ua.activityType")
    List<Object[]> countByActivityType(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT FUNCTION('DATE_FORMAT', ua.timestamp, '%Y-%m-%d'), COUNT(ua) FROM UserActivity ua " +
           "WHERE ua.timestamp BETWEEN :startDate AND :endDate GROUP BY FUNCTION('DATE_FORMAT', ua.timestamp, '%Y-%m-%d')")
    List<Object[]> countByDay(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT AVG(ua.timeSpentSeconds) FROM UserActivity ua WHERE ua.activityType = 'VIEW_ARTICLE' AND ua.timestamp BETWEEN :startDate AND :endDate")
    Double getAverageReadingTime(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
}
