package com.admin.management;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.admin.management")
@EntityScan(basePackages = "com.admin.management.model")
@EnableJpaRepositories(basePackages = "com.admin.management.repository")
public class TestConfiguration {
    // 测试特定配置可以在这里添加
} 