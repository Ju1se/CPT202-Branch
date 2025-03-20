package com.admini.manage.repository;

import com.admini.manage.entity.ReadingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingLogRepository extends JpaRepository<ReadingLog, Long> {
} 