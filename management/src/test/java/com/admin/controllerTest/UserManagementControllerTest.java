package com.admin.controllerTest;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.admin.management.TestConfiguration;
import com.admin.management.model.ReadingLog;
import com.admin.management.model.UserInfo;
import com.admin.management.model.ViolationRecord;
import com.admin.management.model.ViolationType;
import com.admin.management.repository.ReadingLogRepo;
import com.admin.management.repository.UserInfoRepo;
import com.admin.management.repository.ViolationRecordRepo;

@SpringBootTest(classes = TestConfiguration.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserManagementControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UserInfoRepo userInfoRepo;

    @Autowired
    private ReadingLogRepo readingLogRepo;

    @Autowired
    private ViolationRecordRepo violationRecordRepo;
    
    private UserInfo userInfo;
    private ReadingLog readingLog;
    private ViolationRecord violationRecord;
    
    @BeforeEach
    void setUp() {
        userInfoRepo.deleteAll();
        readingLogRepo.deleteAll();
        violationRecordRepo.deleteAll();
        
        // Create UserInfo using setters
        userInfo = new UserInfo();
        userInfo.setUsername("John Doe");
        userInfo.setEmail("johndoe@example.com");
        userInfo.setRole("User");
        userInfo.setActive(true);
        userInfo = userInfoRepo.save(userInfo);
        
        // Create ReadingLog using setters
        readingLog = new ReadingLog();
        readingLog.setSubmissionTime(LocalDateTime.parse("2021-07-01T12:00:00"));
        readingLog.setBookTitle("Book Title");
        readingLog = readingLogRepo.save(readingLog);
        
        // Create ViolationRecord using setters
        violationRecord = new ViolationRecord();
        violationRecord.setViolationType(ViolationType.LATE_RETURN);
        violationRecord = violationRecordRepo.save(violationRecord);
    }

    @Test
    void testGetUserInfos() throws Exception {
        mockMvc.perform(get("/api/userinfos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("johndoe@example.com"));
    }

    @Test
    void testGetReadingLogs() throws Exception {
        mockMvc.perform(get("/api/readinglogs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookTitle").value("Book Title"));
    }

    @Test
    void testGetViolationRecords() throws Exception {
        mockMvc.perform(get("/api/violationrecords"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].violationType").value("LATE_RETURN"));
    }
}

