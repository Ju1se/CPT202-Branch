package com.admin.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.admin.management.model.ViolationRecord;
import com.admin.management.model.ViolationType;
import com.admin.management.model.AppealStatus;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ViolationRecordRepo extends JpaRepository<ViolationRecord, Long> {
    List<ViolationRecord> findByViolationType(ViolationType violationType);
    List<ViolationRecord> findByAppealStatus(AppealStatus appealStatus);
    List<ViolationRecord> findByAccountLocked(boolean accountLocked);
    List<ViolationRecord> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<ViolationRecord> findByPenalty(String penalty);
    
    @Query("SELECT v FROM ViolationRecord v WHERE LOWER(v.details) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ViolationRecord> findByDetailsContainingIgnoreCase(@Param("keyword") String keyword);
    
    List<ViolationRecord> findAllByOrderByTimestampDesc();
}


