package com.example.eventbus.demo.service;

import com.example.eventbus.demo.dao.entity.EventLog;
import com.example.eventbus.demo.service.model.EventLogDto;

import java.util.List;

public interface EventLogService {

    public List<EventLog> queryEventLog(EventLogDto eventLogDto);

    public String saveEventLog(EventLogDto eventLogDto);
}
