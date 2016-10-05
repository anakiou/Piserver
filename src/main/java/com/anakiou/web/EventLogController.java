package com.anakiou.web;

import com.anakiou.domain.EventLog;
import com.anakiou.repository.EventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(value = "/{no}")
    public List<EventLog> getLatest(@PathVariable("no") int no) {
        return eventLogRepository.findAllByOrderByDateCreatedDesc(new PageRequest(0, no));
    }

}
