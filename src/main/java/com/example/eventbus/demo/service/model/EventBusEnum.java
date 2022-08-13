package com.example.eventbus.demo.service.model;

/**
 * event枚举
 */
public enum EventBusEnum {

    SCAN_IP("scanIP","扫描ip","扫描ip事件"),
    SAVE_IP_SQL_MSG("saveIpSqlMsg","存储慢sql信息","存储慢sql信息"),

    ;

    /**
     * 事件名称
     */
    private String code;


    /**
     * 事件中文名称
     */
    private String name;

    /**
     * 描述
     */
    private String descri;


    EventBusEnum(String code, String name, String descri) {
        this.code = code;
        this.name = name;
        this.descri = descri;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public static EventBusEnum getEventBusByCode(String code){
        for (EventBusEnum event:EventBusEnum.values()){
            if(event.getCode().equals(code)){
                return event;
            }
        }
        return null;
    }
}
