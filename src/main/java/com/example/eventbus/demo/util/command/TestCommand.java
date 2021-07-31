package com.example.eventbus.demo.util.command;

import com.example.eventbus.demo.util.chain.BaseCommand;
import com.example.eventbus.demo.util.chain.ContextObject;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @name: event_bus_demo
 * @description:
 * @author: zhxy
 * @create: 2021/7/31 22:16
 **/
@Component
@Slf4j
public class TestCommand extends BaseCommand<ContextObject> {

    @Override
    protected void afterExecute(ContextObject entity) {
        log.info("[TestCommand][afterExecute][{}]",System.currentTimeMillis());
    }

    @Override
    protected boolean excute(ContextObject entity) {
        log.info("[TestCommand][excute][{}]",System.currentTimeMillis());
        return true;
    }

    @Override
    protected void beforeExecute(ContextObject entity) {
        log.info("[TestCommand][beforeExecute][{}]",System.currentTimeMillis());
    }
}
