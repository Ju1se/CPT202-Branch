package com.admin.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.management.model.Appeal;
import com.admin.management.repository.AppealRepo;

@RestController
@RequestMapping("/api/appeals")
public class AppealController {
    
    @Autowired
    private AppealRepo appealRepo;
    
    @GetMapping
    public List<Appeal> getAllAppeals() {
        return appealRepo.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Appeal> getAppealById(@PathVariable Long id) {
        return appealRepo.findById(id)
            .map(appeal -> ResponseEntity.ok(appeal))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/decision")
    public ResponseEntity<Appeal> makeAppealDecision(
            @PathVariable Long id, 
            @RequestBody Appeal updatedAppeal) {
        
        return appealRepo.findById(id)
            .map(appeal -> {
                appeal.setStatus(updatedAppeal.getStatus());
                appeal.setAdminDecision(updatedAppeal.getAdminDecision());
                return ResponseEntity.ok(appealRepo.save(appeal));
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
