package com.admin.management.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private UserFeedbackStatus status;

    private LocalDateTime submissionTime;
    private LocalDateTime lastUpdated;

    @Column(columnDefinition = "TEXT")
    private String adminResponse;

    public UserFeedback() {
    }

    public UserFeedback(Long id, String subject, String content, UserFeedbackStatus status,
            LocalDateTime submissionTime, LocalDateTime lastUpdated, String adminResponse) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.status = status;
        this.submissionTime = submissionTime;
        this.lastUpdated = lastUpdated;
        this.adminResponse = adminResponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserFeedbackStatus getStatus() {
        return status;
    }

    public void setStatus(UserFeedbackStatus status) {
        this.status = status;
    }

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getAdminResponse() {
        return adminResponse;
    }

    public void setAdminResponse(String adminResponse) {
        this.adminResponse = adminResponse;
    }
    
}
