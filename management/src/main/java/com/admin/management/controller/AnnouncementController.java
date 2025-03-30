package com.admin.management.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.admin.management.model.Announcement;
import com.admin.management.repository.AnnouncementRepo;

@RestController
public class AnnouncementController {
    @Autowired
    private AnnouncementRepo announcementRepo;

    @GetMapping("/api/announcements")
    public List<Announcement> getAnnouncements(){
        return announcementRepo.findAll();
    }
    
    @PostMapping("/api/announcements")
    public ResponseEntity<Announcement> createAnnouncement(@RequestBody Announcement announcement){
        announcement.setCreationDate(LocalDateTime.now());
        announcement.setActive(true);

        int maxOrder = 0;
        for (Announcement a : announcementRepo.findAll()){
            if (a.getDisplayOrder() > maxOrder){
                maxOrder = a.getDisplayOrder();
            }
        }
        announcement.setDisplayOrder((maxOrder + 1));

        Announcement savedAnnouncement = announcementRepo.save(announcement);
        return new ResponseEntity<>(savedAnnouncement, HttpStatus.CREATED);
    }

    @PutMapping("/api/announcements/{id}")
    public ResponseEntity<Announcement> updateAnnouncement(
            @PathVariable Long id, 
            @RequestBody Announcement announcement) {
        
        if (!announcementRepo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        announcement.setId(id);
        Announcement updatedAnnouncement = announcementRepo.save(announcement);
        return ResponseEntity.ok(updatedAnnouncement);
    }
    
    @DeleteMapping("/api/announcements/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        if (!announcementRepo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        announcementRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PutMapping("/api/announcements/{id}/order/{order}")
    public ResponseEntity<Announcement> updateAnnouncementOrder(
            @PathVariable Long id, 
            @PathVariable int order) {
        
        if (!announcementRepo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Announcement announcement = announcementRepo.findById(id).get();
        announcement.setDisplayOrder(order);
        Announcement updatedAnnouncement = announcementRepo.save(announcement);
        return ResponseEntity.ok(updatedAnnouncement);
    }

}

