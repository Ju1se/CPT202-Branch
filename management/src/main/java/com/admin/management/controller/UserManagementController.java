package com.admin.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.management.model.ReadingLog;
import com.admin.management.model.UserInfo;
import com.admin.management.model.ViolationRecord;
import com.admin.management.repository.ReadingLogRepo;
import com.admin.management.repository.UserInfoRepo;
import com.admin.management.repository.ViolationRecordRepo;

@RestController
public class UserManagementController {
    @Autowired
    private UserInfoRepo userInfoRepo;

    @Autowired
    private ReadingLogRepo readingLogRepo;

    @Autowired
    private ViolationRecordRepo violationRecordRepo;

    @GetMapping("/api/userinfos")
    public List<UserInfo> getUserInfos(){
        return userInfoRepo.findAll();
    }

    @GetMapping("/api/readinglogs")
    public List<ReadingLog> getReadingLogs(){
        return readingLogRepo.findAll();
    }

    @GetMapping("/api/violationrecords")
    public List<ViolationRecord> getViolationRecords(){
        return violationRecordRepo.findAll();
    }
}
