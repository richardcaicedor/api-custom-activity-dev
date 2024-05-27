package com.wom.customactivityapi.services;

import com.wom.customactivityapi.models.CustomActivityApiLog;
import com.wom.customactivityapi.repository.CustomActivityApiLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CustomActivityApiLogService {
    @Autowired
    private CustomActivityApiLogRepository customActivityApiLogRepository;
    @Async
    public void saveLog(CustomActivityApiLog customActivityApiLog){
        customActivityApiLogRepository.save(customActivityApiLog);
    }
}
