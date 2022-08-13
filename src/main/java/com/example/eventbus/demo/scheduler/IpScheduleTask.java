package com.example.eventbus.demo.scheduler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.eventbus.demo.dao.entity.EventLog;
import com.example.eventbus.demo.dao.entity.IpConfig;
import com.example.eventbus.demo.dao.entity.IpConfigExample;
import com.example.eventbus.demo.dao.mapper.EventLogMapper;
import com.example.eventbus.demo.dao.mapper.IpConfigMapper;
import com.example.eventbus.demo.service.model.EventBusEnum;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @name: task_event
 * @description:
 * @author: zhxy
 * @create: 2022/8/13 23:50
 **/
@Configuration
@EnableScheduling
@Slf4j
public class IpScheduleTask {

    @Autowired
    private EventLogMapper eventLogMapper;

    @Autowired
    private IpConfigMapper ipConfigMapper;

    @Scheduled(fixedRate = 5000)
    public void IpScheduleScanTask(){

        //1.查询出最近10min未被扫描过的ip数据
        List<IpConfig> ipList = getIPList();
        log.info("[ipList][{}]",JSON.toJSONString(ipList));
        //2.构造eventLog对象
        List<EventLog> eventLogList = buildEventLog(ipList);
        log.info("[eventLogList][{}]",JSON.toJSONString(ipList));
        //3.发送mq
        //4.写入eventLog数据库
        //5.更新时间
        for(EventLog eventLog:eventLogList){
            sendMQAndSaveEventBus(eventLog);
        }

    }

    private void sendMQAndSaveEventBus(EventLog eventLog) {
        try {
            //发送mq

            //写入eventLog
            int i = eventLogMapper.insertSelective(eventLog);
            Preconditions.checkState(i>0,"写入eventLog失败");
            //成功后更新时间
            IpConfig ipConfig = JSONObject.parseObject(eventLog.getEventLog(), IpConfig.class);
            ipConfig.setLastScanTime(new Date());
            int update = ipConfigMapper.updateByPrimaryKeySelective(ipConfig);
            Preconditions.checkState(update>0,"更新ipconfig.lastScanTime失败");
        }catch (Exception e){
            log.error("[sendMQAndSaveEventBus][param][{}][error][{}]",JSON.toJSONString(eventLog), ExceptionUtils.getStackTrace(e));
        }
    }

    private List<EventLog> buildEventLog(List<IpConfig> ipList) {
        List<EventLog> eventLogList = new ArrayList<>();
        for(IpConfig ipConfig:ipList){
            EventLog eventLog = new EventLog();
            eventLog.setTraceId(UUID.randomUUID().toString().replace("-",""));
            eventLog.setRfNo(ipConfig.getIp());
            eventLog.setEvent(EventBusEnum.SCAN_IP.getCode());
            eventLog.setEventLog(JSON.toJSONString(ipConfig));
            eventLogList.add(eventLog);
        }
        return eventLogList;
    }

    private List<IpConfig> getIPList() {

        Date tenMinBefore = DateUtils.addMinutes(new Date(), -10);
        IpConfigExample ipConfigExample = new IpConfigExample();
        ipConfigExample.createCriteria()
                .andLastScanTimeLessThanOrEqualTo(tenMinBefore)
                .andIsDelEqualTo(NumberUtils.INTEGER_ZERO);

        return ipConfigMapper.selectByExample(ipConfigExample);
    }

}
