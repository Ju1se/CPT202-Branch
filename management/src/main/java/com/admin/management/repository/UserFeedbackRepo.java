package com.admin.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.management.model.UserFeedback;

@Repository
public interface UserFeedbackRepo extends JpaRepository<UserFeedback, Long>{
   
}
