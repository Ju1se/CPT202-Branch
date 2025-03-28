package com.admin.management.model;

import java.time.LocalDateTime;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;

@Entity
public class ViolationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(length = 255)
    private ViolationType violationType;

    private String details;
    private String penalty;

    @Enumerated(EnumType.STRING)
    private AppealStatus appealStatus;

    private boolean accountLocked;

    public ViolationRecord() {
    }

    public ViolationRecord(Long id, LocalDateTime timestamp, ViolationType violationType, String details,
            String penalty, AppealStatus appealStatus, boolean accountLocked) {
        this.id = id;
        this.timestamp = timestamp;
        this.violationType = violationType;
        this.details = details;
        this.penalty = penalty;
        this.appealStatus = appealStatus;
        this.accountLocked = accountLocked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public ViolationType getViolationType() {
        return violationType;
    }

    public void setViolationType(ViolationType violationType2) {
        this.violationType = violationType2;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public AppealStatus getAppealStatus() {
        return appealStatus;
    }

    public void setAppealStatus(AppealStatus appealStatus) {
        this.appealStatus = appealStatus;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    
}
