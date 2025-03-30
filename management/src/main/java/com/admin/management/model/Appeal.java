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
public class Appeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long violationId;
    
    @Column(columnDefinition = "TEXT")
    private String appealReason;
    
    private LocalDateTime submissionTime;
    
    @Enumerated(EnumType.STRING)
    private AppealStatus status;
    
    @Column(columnDefinition = "TEXT")
    private String adminDecision; 

    public Appeal() {
    }

    public Appeal(Long id, Long violationId, String appealReason, LocalDateTime submissionTime, AppealStatus status,
            String adminDecision) {
        this.id = id;
        this.violationId = violationId;
        this.appealReason = appealReason;
        this.submissionTime = submissionTime;
        this.status = status;
        this.adminDecision = adminDecision;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getViolationId() {
        return violationId;
    }

    public void setViolationId(Long violationId) {
        this.violationId = violationId;
    }

    public String getAppealReason() {
        return appealReason;
    }

    public void setAppealReason(String appealReason) {
        this.appealReason = appealReason;
    }

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }

    public AppealStatus getStatus() {
        return status;
    }

    public void setStatus(AppealStatus status) {
        this.status = status;
    }

    public String getAdminDecision() {
        return adminDecision;
    }

    public void setAdminDecision(String adminDecision) {
        this.adminDecision = adminDecision;
    }

}
