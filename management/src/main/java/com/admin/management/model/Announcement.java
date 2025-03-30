package com.admin.management.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    private LocalDateTime creationDate;
    private LocalDateTime publishDate;
    private LocalDateTime expirationDate;
    
    private boolean active;
    private int displayOrder;

    public Announcement() {
    }

    public Announcement(Long id, String title, String content, LocalDateTime creationDate, LocalDateTime publishDate,
            LocalDateTime expirationDate, boolean active, int displayOrder) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.publishDate = publishDate;
        this.expirationDate = expirationDate;
        this.active = active;
        this.displayOrder = displayOrder;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public LocalDateTime getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public int getDisplayOrder() {
        return displayOrder;
    }
    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
    

}
