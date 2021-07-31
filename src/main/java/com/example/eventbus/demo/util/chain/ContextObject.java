package com.example.eventbus.demo.util.chain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: event_bus_demo
 * @description: 参数基础对象
 * @author: zhxy
 * @create: 2021-05-05 15:15
 **/
public class ContextObject <T,X>{
    /**
     * 模块描述
     */
    @Getter
    @Setter
    private String modelDesc;

    /**
     * 请求参数实体对象
     */
    T requestObject;

    X responseObject;

    public Map<String, CommandWrap<T>> commandMap = new HashMap<>();

    public Map<String,CommandParam> commandParamMap = new HashMap<>();

    public String targetCommandBeanName;



    public void initCommandParamMap(){
        if(MapUtils.isNotEmpty(commandMap)){
            commandMap.keySet().forEach(key->{
                this.commandParamMap.put(key,new CommandParam());
            });
        }
    }

    public T getRequestObject() {
        return requestObject;
    }

    public void setRequestObject(T requestObject) {
        this.requestObject = requestObject;
    }

    public X getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(X responseObject) {
        this.responseObject = responseObject;
    }
}
