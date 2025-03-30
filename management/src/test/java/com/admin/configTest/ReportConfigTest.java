package com.admin.configTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.admin.management.TestConfiguration;
import com.admin.management.config.ReportConfig;

@SpringBootTest(classes = TestConfiguration.class)
@ActiveProfiles("test")
public class ReportConfigTest {
    @Autowired
    private ReportConfig reportConfig;

    @Test
    void testCorsConfigurer() {
        // Arrange
        WebMvcConfigurer configurer = reportConfig.corsConfigurer();
        
        // Assert
        assertNotNull(configurer, "CORS configurer should not be null");
        
        // Test with reflection to verify the cors registry is properly configured
        try {
            // Create a mock registry to pass to the addCorsMappings method
            final CorsRegistry registry = new CorsRegistry();
            
            // Get the addCorsMappings method
            Method method = WebMvcConfigurer.class.getMethod("addCorsMappings", CorsRegistry.class);
            
            // Invoke the method on our configurer
            method.invoke(configurer, registry);
            
            // Unfortunately, there's no easy way to verify the actual mappings in the registry without
            // additional mocking infrastructure. This is a limitation of the test.
            // In a real scenario, we'd use a more integrated test that actually sends CORS requests
            // or we'd use a more sophisticated mocking framework to verify the registry was configured correctly.
            
        } catch (Exception e) {
            fail("Exception should not occur when testing CORS configuration: " + e.getMessage());
        }
    }
}