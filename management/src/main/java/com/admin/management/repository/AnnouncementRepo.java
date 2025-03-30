package com.admin.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.management.model.Announcement;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement, Long>{
    
}
