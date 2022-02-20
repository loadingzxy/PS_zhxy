package com.example.eventbus.demo.util.rule;


import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @name: task_event
 * @description:
 * @author: zhxy
 * @create: 2022/2/20 22:12
 **/
@Slf4j
public class RuleEngine {
    private static final ConcurrentHashMap<String, Method> METHOD_CACHE = new ConcurrentHashMap<>();

    private static final Map<String, String> METHOD_MAP = new HashMap<String, String>() {
        {
            put("&&", "andMethod");
            put("or", "orMethod");
            put("=", "equalMethod");
            put("|", "allMethod");
            put("in", "inMethod");
            put("||", "allMethod");
        }
    };

    //入参存在数组的方法
    private static final List<String> SPECIAL_METHOD = new ArrayList<String>() {
        {
            add("andMethod");
            add("orMethod");
            add("allMethod");
        }
    };

    public static RuleResult andMethod(Object... values) {
        RuleResult result = new RuleResult();
        if (values instanceof Boolean[]) {
            return result;
        }

        if (Arrays.asList(values).contains(false)) {
            return result;
        }
        result.setResult(true);
        return result;
    }

    public static RuleResult orMethod(Object... values) {
        RuleResult result = new RuleResult();
        if (values instanceof Boolean[]) {
            return result;
        }
        if (Arrays.asList(values).contains(true)) {
            result.setResult(true);
            return result;
        }
        return result;
    }

    public static RuleResult equalMethod(Object originValue, Object value) {
        RuleResult result = new RuleResult();
        if ((originValue instanceof Integer) && (value instanceof Integer)) {
            int originValueInt = (Integer) originValue;
            int valueInt = (Integer) value;

            result.setResult(originValueInt == valueInt);
            return result;
        }

        if ((originValue instanceof String) && (value instanceof String)) {
            String originValueInt = (String) originValue;
            String valueInt = (String) value;

            result.setResult(originValueInt.equalsIgnoreCase(valueInt));
            return result;
        }
        return result;
    }

    public static RuleResult allMethod(Object... values) {
        RuleResult result = new RuleResult();
        if (values instanceof Boolean[]) {
            return result;
        }

        List<Integer> ruleMathchList = new ArrayList<>();
        Integer i = 0;
        for (Object value : values) {
            if ((boolean) value) {
                ruleMathchList.add(i);
                result.setResult(true);
            }
            i++;
        }

        result.setRuleMatchList(ruleMathchList);
        return result;
    }

    public static RuleResult inMethod(String targetStr, String originStr) {
        RuleResult result = new RuleResult();
        targetStr = targetStr.toLowerCase();
        originStr = originStr.toLowerCase();

        String[] targetArr = targetStr.split(",");
        for (String target : targetArr) {
            if (!originStr.contains(target)) {
                result.setResult(false);
            }
            return result;
        }

        result.setResult(true);
        return result;

    }

    public static RuleResult ruleParseInner(JSONArray ruleJson) {
        RuleResult result = new RuleResult();
        try {
            String operate = null;
            for (int i = 1; i < ruleJson.size(); i++) {
                operate = ruleJson.getString(0);
                if (!(ruleJson.get(i) instanceof JSONArray)) {
                    break;
                }
                boolean b = ruleParseInner((JSONArray) ruleJson.get(i)).getResult();
                ruleJson.remove(i);
                ruleJson.add(i, b);
            }


            String methodStr = METHOD_MAP.get(operate);
            Method method = null;

            if (null != METHOD_CACHE.get(methodStr)) {
                method = METHOD_CACHE.get(methodStr);
            }else {
                for (Method m : RuleEngine.class.getMethods()){
                    if(m.getName().equals(methodStr)){
                        method = m;
                        METHOD_CACHE.put(methodStr,m);
                    }
                }
            }

            Object[] paramArr = new Object[method.getParameterCount()];
            Object[] objects = new Object[ruleJson.size() - 1];

            for (int i = 0; i < ruleJson.size() - 1 ; i++) {
                objects[i] = ruleJson.get(i+1);
            }
            if(SPECIAL_METHOD.contains(method.getName())) {
                paramArr[0] = objects;
            }else {
                paramArr = objects;
            }

            return (RuleResult) method.invoke(null,paramArr);

        } catch (Exception e) {
            return result;
        }


    }


}
