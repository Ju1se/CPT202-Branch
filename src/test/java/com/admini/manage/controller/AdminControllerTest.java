package com.admini.manage.controller;

import com.admini.manage.entity.ReadingLog;
import com.admini.manage.entity.User;
import com.admini.manage.service.ReadingLogService;
import com.admini.manage.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@WithMockUser(roles = "ADMIN")
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ReadingLogService readingLogService;

    @Test
    public void testGetAllUsers() throws Exception {
        // Given: Create test users
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("testuser1");
        user1.setEmail("test1@example.com");
        user1.setRole(User.UserRole.USER);
        user1.setLocked(false);

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("testuser2");
        user2.setEmail("test2@example.com");
        user2.setRole(User.UserRole.USER);
        user2.setLocked(false);

        List<User> users = Arrays.asList(user1, user2);
        when(userService.getAllUsers()).thenReturn(users);

        // When/Then: Verify the endpoint returns the correct view
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/users"))
                .andExpect(model().attribute("users", users));
    }

    @Test
    public void testLockUser() throws Exception {
        // Given: Setup test user
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setRole(User.UserRole.USER);
        user.setLocked(false);
        when(userService.lockUser(1L)).thenReturn(user);

        // When/Then: Verify the endpoint redirects correctly
        mockMvc.perform(post("/admin/users/1/lock"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));
    }

    @Test
    public void testUnlockUser() throws Exception {
        // Given: Setup test user
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setRole(User.UserRole.USER);
        user.setLocked(true);
        when(userService.unlockUser(1L)).thenReturn(user);

        // When/Then: Verify the endpoint redirects correctly
        mockMvc.perform(post("/admin/users/1/unlock"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));
    }

    @Test
    public void testGetAllReadingLogs() throws Exception {
        // Given: Create test reading logs
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        ReadingLog log1 = new ReadingLog();
        log1.setId(1L);
        log1.setUser(user);
        log1.setTitle("Test Book 1");
        log1.setAuthor("Test Author 1");
        log1.setDate(LocalDate.now());
        log1.setTimeSpent(60);
        log1.setStatus(ReadingLog.ReadingStatus.PENDING);

        ReadingLog log2 = new ReadingLog();
        log2.setId(2L);
        log2.setUser(user);
        log2.setTitle("Test Book 2");
        log2.setAuthor("Test Author 2");
        log2.setDate(LocalDate.now());
        log2.setTimeSpent(120);
        log2.setStatus(ReadingLog.ReadingStatus.PENDING);

        List<ReadingLog> logs = Arrays.asList(log1, log2);
        when(readingLogService.getAllReadingLogs()).thenReturn(logs);

        // When/Then: Verify the endpoint returns the correct view
        mockMvc.perform(get("/admin/reading-logs"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/reading-logs"))
                .andExpect(model().attribute("readingLogs", logs));
    }

    @Test
    public void testApproveReadingLog() throws Exception {
        // Given: Setup test reading log
        ReadingLog log = new ReadingLog();
        log.setId(1L);
        log.setTitle("Test Book");
        log.setStatus(ReadingLog.ReadingStatus.PENDING);
        when(readingLogService.approveReadingLog(1L)).thenReturn(log);

        // When/Then: Verify the endpoint redirects correctly
        mockMvc.perform(post("/admin/reading-logs/1/approve"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/reading-logs"));
    }

    @Test
    public void testRejectReadingLog() throws Exception {
        // Given: Setup test reading log
        ReadingLog log = new ReadingLog();
        log.setId(1L);
        log.setTitle("Test Book");
        log.setStatus(ReadingLog.ReadingStatus.PENDING);
        when(readingLogService.rejectReadingLog(1L)).thenReturn(log);

        // When/Then: Verify the endpoint redirects correctly
        mockMvc.perform(post("/admin/reading-logs/1/reject"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/reading-logs"));
    }

    @Test
    public void testDeleteReadingLog() throws Exception {
        // When/Then: Verify the endpoint redirects correctly
        mockMvc.perform(post("/admin/reading-logs/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/reading-logs"));
    }
} 