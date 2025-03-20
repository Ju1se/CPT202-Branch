package com.admini.manage.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "reading_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadingLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String author;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(name = "time_spent", nullable = false)
    private int timeSpent;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReadingStatus status;
    
    public enum ReadingStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
} 