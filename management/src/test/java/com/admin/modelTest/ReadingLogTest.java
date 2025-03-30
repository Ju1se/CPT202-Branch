package com.admin.modelTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.admin.management.model.ReadingLog;
import com.admin.management.model.ReadingLogStatus;

class ReadingLogTest {

    @Test
    void testDefaultConstructor() {
        ReadingLog readingLog = new ReadingLog();
        
        assertNull(readingLog.getId());
        assertNull(readingLog.getSubmissionTime());
        assertNull(readingLog.getBookTitle());
        assertNull(readingLog.getPagesRead());
        assertNull(readingLog.getNotes());
        assertNull(readingLog.getStatus());
        assertNull(readingLog.getAdminComments());
    }

    @Test
    void testParameterizedConstructorAndGetters() {
        Long id = 1L;
        LocalDateTime submissionTime = LocalDateTime.now();
        String bookTitle = "Clean Code";
        Integer pagesRead = 150;
        String notes = "Great book about software craftsmanship";
        ReadingLogStatus status = ReadingLogStatus.APPROVED; 
        String adminComments = "Well documented reading progress";
        
        ReadingLog readingLog = new ReadingLog(id, submissionTime, bookTitle, pagesRead, 
                                              notes, status, adminComments);

        assertEquals(id, readingLog.getId());
        assertEquals(submissionTime, readingLog.getSubmissionTime());
        assertEquals(bookTitle, readingLog.getBookTitle());
        assertEquals(pagesRead, readingLog.getPagesRead());
        assertEquals(notes, readingLog.getNotes());
        assertEquals(status, readingLog.getStatus());
        assertEquals(adminComments, readingLog.getAdminComments());
    }

    @Test
    void testSettersAndGetters() {
        ReadingLog readingLog = new ReadingLog();
        Long id = 2L;
        LocalDateTime submissionTime = LocalDateTime.of(2025, 3, 15, 14, 30);
        String bookTitle = "Design Patterns";
        Integer pagesRead = 75;
        String notes = "Learning about factory patterns";
        ReadingLogStatus status = ReadingLogStatus.PENDING; 
        String adminComments = "Waiting for verification";
        
        readingLog.setId(id);
        readingLog.setSubmissionTime(submissionTime);
        readingLog.setBookTitle(bookTitle);
        readingLog.setPagesRead(pagesRead);
        readingLog.setNotes(notes);
        readingLog.setStatus(status);
        readingLog.setAdminComments(adminComments);
        
        assertEquals(id, readingLog.getId());
        assertEquals(submissionTime, readingLog.getSubmissionTime());
        assertEquals(bookTitle, readingLog.getBookTitle());
        assertEquals(pagesRead, readingLog.getPagesRead());
        assertEquals(notes, readingLog.getNotes());
        assertEquals(status, readingLog.getStatus());
        assertEquals(adminComments, readingLog.getAdminComments());
    }
    
    @Test
    void testPagesReadWithZeroValue() {
        ReadingLog readingLog = new ReadingLog();
        Integer pagesRead = 0;
        
        readingLog.setPagesRead(pagesRead);
        
        assertEquals(pagesRead, readingLog.getPagesRead());
    }
    
    @Test
    void testBookTitleWithEmptyString() {
        ReadingLog readingLog = new ReadingLog();
        String emptyTitle = "";
        
        readingLog.setBookTitle(emptyTitle);
        
        assertEquals(emptyTitle, readingLog.getBookTitle());
        assertNotNull(readingLog.getBookTitle());
    }
}
