package com.example.eventbus.demo.controller;

import com.example.eventbus.demo.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name: event_bus_demo
 * @description:
 * @author: zhxy
 * @create: 2021/7/31 21:47
 **/
@RestController
@Slf4j
public class HellowController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/hello")
    public String hello(){
        log.info("[HellowController][hello]");
        helloService.hellowTestTask("test"+System.currentTimeMillis());
        return "hello test demo for event bus";
    }
}
