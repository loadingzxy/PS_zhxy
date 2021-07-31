package com.example.eventbus.demo.util.chain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @program: event_bus_demo
 * @description: commond包装类 commond和下一步指针
 * @author: zhxy
 * @create: 2021-05-05 15:18
 **/
@Getter
@Setter
public class CommandWrap <T>{
    private BaseCommand<T> baseCommand;

    private BaseCommand<T> next = null;
}
