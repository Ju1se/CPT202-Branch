package com.admini.manage.service;

import com.admini.manage.entity.ReadingLog;
import com.admini.manage.repository.ReadingLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReadingLogService {

    private final ReadingLogRepository readingLogRepository;

    @Autowired
    public ReadingLogService(ReadingLogRepository readingLogRepository) {
        this.readingLogRepository = readingLogRepository;
    }

    public List<ReadingLog> getAllReadingLogs() {
        return readingLogRepository.findAll();
    }

    @Transactional
    public ReadingLog approveReadingLog(Long id) {
        ReadingLog readingLog = readingLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reading log not found with id: " + id));
        readingLog.setStatus(ReadingLog.ReadingStatus.APPROVED);
        return readingLogRepository.save(readingLog);
    }

    @Transactional
    public ReadingLog rejectReadingLog(Long id) {
        ReadingLog readingLog = readingLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reading log not found with id: " + id));
        readingLog.setStatus(ReadingLog.ReadingStatus.REJECTED);
        return readingLogRepository.save(readingLog);
    }

    @Transactional
    public void deleteReadingLog(Long id) {
        if (!readingLogRepository.existsById(id)) {
            throw new RuntimeException("Reading log not found with id: " + id);
        }
        readingLogRepository.deleteById(id);
    }
} 