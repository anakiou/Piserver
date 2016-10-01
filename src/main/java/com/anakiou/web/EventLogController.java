package com.anakiou.web;

import com.anakiou.domain.EventLog;
import com.anakiou.repository.EventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventLogController {

    private final EventLogRepository eventLogRepository;

    @Autowired
    public EventLogController(EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    @GetMapping
    public List<EventLog> get() {
      return eventLogRepository.findAll();
    }

}
