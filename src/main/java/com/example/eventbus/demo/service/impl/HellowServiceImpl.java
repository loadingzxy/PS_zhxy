package com.example.eventbus.demo.service.impl;

import com.example.eventbus.demo.service.HelloService;
import com.example.eventbus.demo.util.chain.ContextObject;
import com.example.eventbus.demo.util.task.TestTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @name: event_bus_demo
 * @description:
 * @author: zhxy
 * @create: 2021/7/31 22:20
 **/
@Service
@Slf4j
public class HellowServiceImpl implements HelloService {

    @Autowired
    private TestTask testTask;

    @Override
    public String hellowTestTask(String hellow) {
        try {
            ContextObject contextObject = new ContextObject();
            contextObject.setRequestObject(hellow);
            testTask.process(contextObject);
            log.info("[hellowTestTask][sucess][{}]",System.currentTimeMillis());
        }catch (Exception e){
            log.error("[hellowTestTask][exception][{}]", ExceptionUtils.getStackTrace(e));
        }

        return null;
    }
}
