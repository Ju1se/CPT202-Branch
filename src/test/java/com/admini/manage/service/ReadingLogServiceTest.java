package com.admini.manage.service;

import com.admini.manage.entity.ReadingLog;
import com.admini.manage.entity.User;
import com.admini.manage.repository.ReadingLogRepository;
import com.admini.manage.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ReadingLogServiceTest {

    @Autowired
    private ReadingLogService readingLogService;

    @Autowired
    private ReadingLogRepository readingLogRepository;

    @Autowired
    private UserRepository userRepository;

    private User createTestUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole(User.UserRole.USER);
        user.setLocked(false);
        return userRepository.save(user);
    }

    private ReadingLog createTestReadingLog(User user) {
        ReadingLog log = new ReadingLog();
        log.setUser(user);
        log.setTitle("Test Book");
        log.setAuthor("Test Author");
        log.setDate(LocalDate.now());
        log.setTimeSpent(60);
        log.setNotes("Test notes");
        log.setStatus(ReadingLog.ReadingStatus.PENDING);
        return readingLogRepository.save(log);
    }

    @Test
    public void testGetAllReadingLogs() {
        // Given: Create test user and reading logs
        User user = createTestUser();
        ReadingLog log1 = createTestReadingLog(user);
        ReadingLog log2 = createTestReadingLog(user);

        // When: Get all reading logs
        List<ReadingLog> logs = readingLogService.getAllReadingLogs();

        // Then: Verify all logs are returned
        assertNotNull(logs);
        assertTrue(logs.size() >= 2);
        assertTrue(logs.stream().anyMatch(l -> l.getId().equals(log1.getId())));
        assertTrue(logs.stream().anyMatch(l -> l.getId().equals(log2.getId())));
    }

    @Test
    public void testApproveReadingLog() {
        // Given: Create a test reading log
        User user = createTestUser();
        ReadingLog log = createTestReadingLog(user);

        // When: Approve the reading log
        ReadingLog approvedLog = readingLogService.approveReadingLog(log.getId());

        // Then: Verify the log is approved
        assertNotNull(approvedLog);
        assertEquals(ReadingLog.ReadingStatus.APPROVED, approvedLog.getStatus());

        // Verify the change is persisted
        ReadingLog retrievedLog = readingLogRepository.findById(log.getId()).orElse(null);
        assertNotNull(retrievedLog);
        assertEquals(ReadingLog.ReadingStatus.APPROVED, retrievedLog.getStatus());
    }

    @Test
    public void testRejectReadingLog() {
        // Given: Create a test reading log
        User user = createTestUser();
        ReadingLog log = createTestReadingLog(user);

        // When: Reject the reading log
        ReadingLog rejectedLog = readingLogService.rejectReadingLog(log.getId());

        // Then: Verify the log is rejected
        assertNotNull(rejectedLog);
        assertEquals(ReadingLog.ReadingStatus.REJECTED, rejectedLog.getStatus());

        // Verify the change is persisted
        ReadingLog retrievedLog = readingLogRepository.findById(log.getId()).orElse(null);
        assertNotNull(retrievedLog);
        assertEquals(ReadingLog.ReadingStatus.REJECTED, retrievedLog.getStatus());
    }

    @Test
    public void testDeleteReadingLog() {
        // Given: Create a test reading log
        User user = createTestUser();
        ReadingLog log = createTestReadingLog(user);

        // When: Delete the reading log
        readingLogService.deleteReadingLog(log.getId());

        // Then: Verify the log is deleted
        assertFalse(readingLogRepository.existsById(log.getId()));
    }
} 