package com.anakiou.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);

    private final PifaceService pifaceService;

    @Autowired
    public ScheduledTasks(PifaceService pifaceService) {
        this.pifaceService = pifaceService;
    }

    @Scheduled(fixedRate = 5000)
    public void updateIOTask(){
        pifaceService.updateIO();
    }
}
