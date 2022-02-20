package com.example.eventbus.demo.util.rule;

import lombok.Data;

import java.util.List;

/**
 * @name: task_event
 * @description:
 * @author: zhxy
 * @create: 2022/2/20 22:17
 **/
@Data
public class RuleResult {

    private Boolean result;

    private List<Integer> ruleMatchList;

    RuleResult(){
        this.result = false;
    }
}
