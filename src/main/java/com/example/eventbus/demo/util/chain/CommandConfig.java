package com.example.eventbus.demo.util.chain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: event_bus_demo
 * @description: command配置
 * @author: zhxy
 * @create: 2021-05-05 15:20
 **/
@Data
public class CommandConfig implements Serializable {

    private static final long serialVersionUID = 4336021719242032480L;

    private String beanName;

    private int maxRetryTime = 1;

    private boolean transaction = false;
}
