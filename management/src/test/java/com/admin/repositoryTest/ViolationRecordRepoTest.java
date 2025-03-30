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
import com.admin.management.model.ViolationRecord;
import com.admin.management.model.ViolationType;
import com.admin.management.repository.ViolationRecordRepo;

@DataJpaTest
@ContextConfiguration(classes = TestConfiguration.class)
@ActiveProfiles("test")
public class ViolationRecordRepoTest {

    @Autowired
    private ViolationRecordRepo violationRecordRepo;

    @Test
    void testSaveAndFindById() {
        // 创建并保存测试数据
        ViolationRecord record = new ViolationRecord();
        record.setViolationType(ViolationType.LATE_RETURN);
        record.setTimestamp(LocalDateTime.now());
        record.setDetails("测试违规记录");
        
        ViolationRecord saved = violationRecordRepo.save(record);
        
        // 测试查询
        Optional<ViolationRecord> found = violationRecordRepo.findById(saved.getId());
        
        // 验证
        assertNotNull(found);
        assertEquals(ViolationType.LATE_RETURN, found.get().getViolationType());
    }
} 