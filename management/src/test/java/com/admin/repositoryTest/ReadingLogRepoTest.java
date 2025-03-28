package com.admin.repositoryTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.admin.management.TestConfiguration;
import com.admin.management.model.ReadingLog;
import com.admin.management.model.ReadingLogStatus;
import com.admin.management.repository.ReadingLogRepo;

@DataJpaTest
@ContextConfiguration(classes = TestConfiguration.class)
@ActiveProfiles("test")
public class ReadingLogRepoTest {

    @Autowired
    private ReadingLogRepo readingLogRepo;

    @Test
    void testSaveAndFindById() {
        // 创建并保存测试数据
        ReadingLog readingLog = new ReadingLog();
        readingLog.setBookTitle("测试书籍");
        readingLog.setSubmissionTime(LocalDateTime.now());
        readingLog.setStatus(ReadingLogStatus.PENDING);
        
        ReadingLog savedLog = readingLogRepo.save(readingLog);
        
        // 测试查询
        Optional<ReadingLog> found = readingLogRepo.findById(savedLog.getId());
        
        // 验证
        assertNotNull(found);
        assertEquals("测试书籍", found.get().getBookTitle());
    }
}
