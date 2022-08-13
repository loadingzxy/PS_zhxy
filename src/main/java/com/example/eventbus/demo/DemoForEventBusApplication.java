package com.example.eventbus.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.eventbus.demo.dao.mapper")
public class DemoForEventBusApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoForEventBusApplication.class, args);
    }

}
