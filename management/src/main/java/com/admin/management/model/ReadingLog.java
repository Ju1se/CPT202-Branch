package com.admin.management.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ReadingLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime submissionTime;
    private String bookTitle;
    private Integer pagesRead;
    private String notes;

    @Enumerated(EnumType.STRING)
    private ReadingLogStatus status;
    private String adminComments;

    public ReadingLog() {
    }
    
    public ReadingLog(Long id, LocalDateTime submissionTime, String bookTitle, Integer pagesRead, String notes,
            ReadingLogStatus status, String adminComments) {
        this.id = id;
        this.submissionTime = submissionTime;
        this.bookTitle = bookTitle;
        this.pagesRead = pagesRead;
        this.notes = notes;
        this.status = status;
        this.adminComments = adminComments;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }
    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }
    public String getBookTitle() {
        return bookTitle;
    }
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    public Integer getPagesRead() {
        return pagesRead;
    }
    public void setPagesRead(Integer pagesRead) {
        this.pagesRead = pagesRead;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public ReadingLogStatus getStatus() {
        return status;
    }
    public void setStatus(ReadingLogStatus status) {
        this.status = status;
    }
    public String getAdminComments() {
        return adminComments;
    }
    public void setAdminComments(String adminComments) {
        this.adminComments = adminComments;
    }

    

}
