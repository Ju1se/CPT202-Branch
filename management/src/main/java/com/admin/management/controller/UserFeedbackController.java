package com.admin.management.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.management.model.UserFeedback;
import com.admin.management.repository.UserFeedbackRepo;

@RestController
@RequestMapping("/api/userfeedback")
public class UserFeedbackController {
    
    @Autowired
    private UserFeedbackRepo userFeedbackRepo;

    @GetMapping
    public List<UserFeedback> getAllUserFeedback() {
        return userFeedbackRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserFeedback> getUserFeedbackById(@PathVariable Long id){
        return userFeedbackRepo.findById(id)
                .map(userFeedback -> ResponseEntity.ok().body(userFeedback))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<UserFeedback> updateFeedbackStatus(
            @PathVariable Long id, 
            @RequestBody UserFeedback updatedFeedback) {
        
        return userFeedbackRepo.findById(id)
            .map(feedback -> {
                feedback.setStatus(updatedFeedback.getStatus());
                feedback.setLastUpdated(LocalDateTime.now());
                return ResponseEntity.ok(userFeedbackRepo.save(feedback));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/response")
    public ResponseEntity<UserFeedback> updateFeedbackResponse(
            @PathVariable Long id, 
            @RequestBody UserFeedback updatedFeedback) {
        
        return userFeedbackRepo.findById(id)
            .map(feedback -> {
                feedback.setAdminResponse(updatedFeedback.getAdminResponse());
                feedback.setLastUpdated(LocalDateTime.now());
                return ResponseEntity.ok(userFeedbackRepo.save(feedback));
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
