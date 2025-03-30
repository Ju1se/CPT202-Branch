package com.admin.repositoryTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.admin.management.TestConfiguration;
import com.admin.management.model.UserInfo;
import com.admin.management.repository.UserInfoRepo;

@DataJpaTest
@ContextConfiguration(classes = TestConfiguration.class)
@ActiveProfiles("test")
public class UserInfoRepoTest {

    @Autowired
    private UserInfoRepo userInfoRepo;

    @Test
    void testSaveAndFindById() {
        // 创建并保存测试数据
        UserInfo user = new UserInfo();
        user.setUsername("测试用户");
        user.setEmail("test@example.com");
        user.setRole("User");
        user.setActive(true);
        
        UserInfo savedUser = userInfoRepo.save(user);
        
        // 测试查询
        Optional<UserInfo> found = userInfoRepo.findById(savedUser.getId());
        
        // 验证
        assertNotNull(found);
        assertEquals("测试用户", found.get().getUsername());
        assertEquals("test@example.com", found.get().getEmail());
    }
} 