package com.example.eventbus.demo.util.chain;

import lombok.Data;

/**
 * @program: event_bus_demo
 * @description: 责任链通用参数
 * @author: zhxy
 * @create: 2021-05-05 15:29
 **/
@Data
public class CommandParam {
    /**
     * 当前重试次数
     */
    public int currentRetrytime = 0;

    /**
     * 开始执行时间
     */
    private Long startTime;
}
