package com.admin.management;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestConfiguration.class)
@ActiveProfiles("test")
class ManagementApplicationTests {

	@Test
	void contextLoads() {
		// 测试应用程序上下文能否正常加载
	}

}
