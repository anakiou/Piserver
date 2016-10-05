package com.anakiou.repository;

import com.anakiou.domain.EventLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.events.Event;

import java.util.List;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {

    List<EventLog> findAllByOrderByDateCreatedDesc(Pageable pageable);
}
