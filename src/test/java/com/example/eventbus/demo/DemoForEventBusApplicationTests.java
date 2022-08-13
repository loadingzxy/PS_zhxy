package com.example.eventbus.demo;

import com.example.eventbus.demo.dao.entity.EventLog;
import com.example.eventbus.demo.dao.entity.EventLogExample;
import com.example.eventbus.demo.dao.entity.IpConfig;
import com.example.eventbus.demo.dao.entity.IpConfigExample;
import com.example.eventbus.demo.dao.mapper.EventLogMapper;
import com.example.eventbus.demo.dao.mapper.IpConfigMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoForEventBusApplicationTests {

    @Autowired
    private IpConfigMapper mapper;

    @Autowired
    private EventLogMapper eventLogMapper;

    @Test
    public void contextLoads() {
        IpConfigExample ipConfigExample = new IpConfigExample();
        ipConfigExample.createCriteria();
        List<IpConfig> ipConfigs = mapper.selectByExample(ipConfigExample);
        System.out.println();
    }

    @Test
    public void testcontextLoads() {

        EventLogExample example = new EventLogExample();
        example.createCriteria();
        List<EventLog> eventLogs = eventLogMapper.selectByExample(example);
        System.out.println();
    }







}
