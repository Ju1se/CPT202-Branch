package com.admin.modelTest;

import org.junit.jupiter.api.Test;

import com.admin.management.model.AppealStatus;
import com.admin.management.model.ViolationRecord;
import com.admin.management.model.ViolationType;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class ViolationRecordTest {

    @Test
    void testDefaultConstructor() {
        // Act
        ViolationRecord violationRecord = new ViolationRecord();
        
        // Assert
        assertNull(violationRecord.getId());
        assertNull(violationRecord.getTimestamp());
        assertNull(violationRecord.getViolationType());
        assertNull(violationRecord.getDetails());
        assertNull(violationRecord.getPenalty());
        assertNull(violationRecord.getAppealStatus());
        assertFalse(violationRecord.isAccountLocked());
    }

    @Test
    void testParameterizedConstructorAndGetters() {
        // Arrange
        Long id = 1L;
        LocalDateTime timestamp = LocalDateTime.now();
        ViolationType violationType = ViolationType.PLAGIARISM; // Assuming this is a value in the enum
        String details = "Copied code from another student";
        String penalty = "Zero marks for assignment";
        AppealStatus appealStatus = AppealStatus.PENDING; // Assuming this is a value in the enum
        boolean accountLocked = true;
        
        // Act
        ViolationRecord violationRecord = new ViolationRecord(id, timestamp, violationType, details,
                                                            penalty, appealStatus, accountLocked);
        
        // Assert
        assertEquals(id, violationRecord.getId());
        assertEquals(timestamp, violationRecord.getTimestamp());
        assertEquals(violationType, violationRecord.getViolationType());
        assertEquals(details, violationRecord.getDetails());
        assertEquals(penalty, violationRecord.getPenalty());
        assertEquals(appealStatus, violationRecord.getAppealStatus());
        assertTrue(violationRecord.isAccountLocked());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        ViolationRecord violationRecord = new ViolationRecord();
        Long id = 2L;
        LocalDateTime timestamp = LocalDateTime.of(2025, 3, 20, 15, 30);
        ViolationType violationType = ViolationType.CHEATING; // Assuming this is a value in the enum
        String details = "Used unauthorized materials during exam";
        String penalty = "Course failure";
        AppealStatus appealStatus = AppealStatus.REJECTED; // Assuming this is a value in the enum
        boolean accountLocked = false;
        
        // Act
        violationRecord.setId(id);
        violationRecord.setTimestamp(timestamp);
        violationRecord.setViolationType(violationType);
        violationRecord.setDetails(details);
        violationRecord.setPenalty(penalty);
        violationRecord.setAppealStatus(appealStatus);
        violationRecord.setAccountLocked(accountLocked);
        
        // Assert
        assertEquals(id, violationRecord.getId());
        assertEquals(timestamp, violationRecord.getTimestamp());
        assertEquals(violationType, violationRecord.getViolationType());
        assertEquals(details, violationRecord.getDetails());
        assertEquals(penalty, violationRecord.getPenalty());
        assertEquals(appealStatus, violationRecord.getAppealStatus());
        assertFalse(violationRecord.isAccountLocked());
    }
    
    @Test
    void testAccountLockedToggle() {
        // Arrange
        ViolationRecord violationRecord = new ViolationRecord();
        
        // Act & Assert - Initially false
        assertFalse(violationRecord.isAccountLocked());
        
        // Act - Set to true
        violationRecord.setAccountLocked(true);
        
        // Assert - Now true
        assertTrue(violationRecord.isAccountLocked());
        
        // Act - Toggle back to false
        violationRecord.setAccountLocked(false);
        
        // Assert - Now false again
        assertFalse(violationRecord.isAccountLocked());
    }
    
    @Test
    void testEmptyStrings() {
        // Arrange
        ViolationRecord violationRecord = new ViolationRecord();
        
        // Act
        violationRecord.setDetails("");
        violationRecord.setPenalty("");
        
        // Assert
        assertEquals("", violationRecord.getDetails());
        assertEquals("", violationRecord.getPenalty());
        assertNotNull(violationRecord.getDetails());
        assertNotNull(violationRecord.getPenalty());
    }
}
