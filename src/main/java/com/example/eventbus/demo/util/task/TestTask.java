package com.example.eventbus.demo.util.task;

import com.example.eventbus.demo.util.chain.BaseTask;
import com.example.eventbus.demo.util.chain.ContextObject;
import com.example.eventbus.demo.util.chain.TaskChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @name: event_bus_demo
 * @description: 测试task
 * @author: zhxy
 * @create: 2021/7/31 22:13
 **/

@Component
@TaskChain(commands = {"testCommand"})
@Slf4j
public class TestTask extends BaseTask<ContextObject> {
    @Override
    protected void afterExecute(ContextObject entity) {
        log.info("[TestTask][afterExecute][time][SysTime][{}]",System.currentTimeMillis());
    }

    @Override
    protected void beforeExecute(ContextObject entity) {
        log.info("[TestTask][beforeExecute][time][SysTime][{}]",System.currentTimeMillis());
    }
}
