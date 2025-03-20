package com.admini.manage.integration;

import com.admini.manage.entity.ReadingLog;
import com.admini.manage.entity.User;
import com.admini.manage.repository.ReadingLogRepository;
import com.admini.manage.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(roles = "ADMIN")
@ActiveProfiles("test")
public class AdminIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReadingLogRepository readingLogRepository;

    private User testUser;
    private ReadingLog testReadingLog;

    @BeforeEach
    void setUp() {
        // Create test user
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");
        testUser.setRole(User.UserRole.USER);
        testUser.setLocked(false);
        testUser = userRepository.save(testUser);

        // Create test reading log
        testReadingLog = new ReadingLog();
        testReadingLog.setUser(testUser);
        testReadingLog.setTitle("Test Book");
        testReadingLog.setAuthor("Test Author");
        testReadingLog.setDate(LocalDate.now());
        testReadingLog.setTimeSpent(60);
        testReadingLog.setNotes("Test notes");
        testReadingLog.setStatus(ReadingLog.ReadingStatus.PENDING);
        testReadingLog = readingLogRepository.save(testReadingLog);
    }

    @Test
    void testCompleteUserManagementFlow() throws Exception {
        // 1. Get all users
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/users"))
                .andExpect(model().attributeExists("users"));

        // 2. Lock user
        mockMvc.perform(post("/admin/users/" + testUser.getId() + "/lock"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));

        // Verify user is locked in database
        User lockedUser = userRepository.findById(testUser.getId()).orElse(null);
        assertNotNull(lockedUser);
        assertTrue(lockedUser.isLocked());

        // 3. Unlock user
        mockMvc.perform(post("/admin/users/" + testUser.getId() + "/unlock"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));

        // Verify user is unlocked in database
        User unlockedUser = userRepository.findById(testUser.getId()).orElse(null);
        assertNotNull(unlockedUser);
        assertFalse(unlockedUser.isLocked());
    }

    @Test
    void testCompleteReadingLogManagementFlow() throws Exception {
        // 1. Get all reading logs
        mockMvc.perform(get("/admin/reading-logs"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/reading-logs"))
                .andExpect(model().attributeExists("readingLogs"));

        // 2. Approve reading log
        mockMvc.perform(post("/admin/reading-logs/" + testReadingLog.getId() + "/approve"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/reading-logs"));

        // Verify reading log is approved in database
        ReadingLog approvedLog = readingLogRepository.findById(testReadingLog.getId()).orElse(null);
        assertNotNull(approvedLog);
        assertEquals(ReadingLog.ReadingStatus.APPROVED, approvedLog.getStatus());

        // 3. Reject reading log
        mockMvc.perform(post("/admin/reading-logs/" + testReadingLog.getId() + "/reject"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/reading-logs"));

        // Verify reading log is rejected in database
        ReadingLog rejectedLog = readingLogRepository.findById(testReadingLog.getId()).orElse(null);
        assertNotNull(rejectedLog);
        assertEquals(ReadingLog.ReadingStatus.REJECTED, rejectedLog.getStatus());

        // 4. Delete reading log
        mockMvc.perform(post("/admin/reading-logs/" + testReadingLog.getId() + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/reading-logs"));

        // Verify reading log is deleted from database
        assertFalse(readingLogRepository.existsById(testReadingLog.getId()));
    }

    @Test
    void testUnauthorizedAccess() throws Exception {
        // Test without admin role
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testNonExistentUserOperations() throws Exception {
        // Test operations on non-existent user
        mockMvc.perform(post("/admin/users/999/lock"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));

        mockMvc.perform(post("/admin/users/999/unlock"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));
    }

    @Test
    void testNonExistentReadingLogOperations() throws Exception {
        // Test operations on non-existent reading log
        mockMvc.perform(post("/admin/reading-logs/999/approve"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/reading-logs"));

        mockMvc.perform(post("/admin/reading-logs/999/reject"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/reading-logs"));

        mockMvc.perform(post("/admin/reading-logs/999/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/reading-logs"));
    }
} 