package com.admin.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.management.model.Appeal;

@Repository
public interface AppealRepo extends JpaRepository<Appeal, Long>{
    
}
