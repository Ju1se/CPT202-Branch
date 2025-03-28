package com.admin.management.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.admin.management.model.ReadingLog;
import com.admin.management.model.ReadingLogStatus;

@Repository
public interface ReadingLogRepo extends JpaRepository<ReadingLog, Long> {
    
    // 根据状态查找阅读日志
    List<ReadingLog> findByStatus(ReadingLogStatus status);
    
    // 根据书籍标题查找阅读日志
    List<ReadingLog> findByBookTitleContaining(String bookTitle);
    
    // 查找特定时间段内的阅读日志
    List<ReadingLog> findBySubmissionTimeBetween(LocalDateTime start, LocalDateTime end);
    
    // 查找已读页数大于指定值的阅读日志
    List<ReadingLog> findByPagesReadGreaterThan(Integer pagesRead);
    
    // 按提交时间降序排列的阅读日志
    List<ReadingLog> findAllByOrderBySubmissionTimeDesc();
    
    // 根据状态和书籍标题查找阅读日志
    List<ReadingLog> findByStatusAndBookTitleContaining(ReadingLogStatus status, String bookTitle);
    
    // 查找有管理员评论的阅读日志
    List<ReadingLog> findByAdminCommentsIsNotNull();
    
    // 使用JPQL查询获取每本书的总阅读页数
    @Query("SELECT r.bookTitle, SUM(r.pagesRead) FROM ReadingLog r GROUP BY r.bookTitle")
    List<Object[]> findTotalPagesReadByBook();
}