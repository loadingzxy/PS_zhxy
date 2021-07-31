package com.example.eventbus.demo.util.chain;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StopWatch;

/**
 * @program: event_bus_demo
 * @description: 责任链基础执行类
 * @author: zhxy
 * @create: 2021-05-05 15:19
 **/
@Slf4j
public abstract class BaseCommand<T> {

    private CommandConfig config = new CommandConfig();

    final public CommandConfig getConfig() {
        return config;
    }

    /**
     * 责任链流转控制
     *
     * @param entity
     */
    final public void process(T entity) {
        ContextObject contextObject = (ContextObject) entity;
        CommandParam commandParam = (CommandParam) contextObject.commandParamMap.get(this.config.getBeanName());

        if (commandParam.currentRetrytime >= this.config.getMaxRetryTime()) {
            return;
        }

        commandParam.currentRetrytime++;
        commandParam.setStartTime(System.currentTimeMillis());

        boolean executeRes = false;
        if (config.isTransaction()) {
            executeRes = doProcessTransaction(entity);
        }else {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start(this.getClass().getSimpleName());
            executeRes = doProcess(entity);
            stopWatch.stop();
            log.info("[CommandProcess][spendTime][]",stopWatch.prettyPrint());
        }

        if(executeRes){
            BaseCommand<T> next = this.next(contextObject);
            if(next !=null){
                next.process(entity);
            }
        }

    }

    //    @Transactional
    protected boolean doProcessTransaction(T entity) {
        return doProcess(entity);
    }

    protected boolean doProcess(T entity) {
        this.beforeExecute(entity);
        boolean executeResult = this.excute(entity);
        this.afterExecute(entity);
        return executeResult;
    }

    protected abstract void afterExecute(T entity);

    protected abstract boolean excute(T entity);

    protected abstract void beforeExecute(T entity);

    private BaseCommand<T> next(ContextObject contextObject) {
        if (StringUtils.isNotBlank(contextObject.targetCommandBeanName)) {
            CommandWrap<T> commandWrap = (CommandWrap<T>) contextObject.commandMap.get(contextObject.targetCommandBeanName);
            contextObject.targetCommandBeanName = null;
            if (commandWrap != null) {
                return commandWrap.getBaseCommand();
            }
        } else {
            CommandWrap<T> commandWrap = (CommandWrap<T>) contextObject.commandMap.get(this.config.getBeanName());
            if (commandWrap != null) {
                return commandWrap.getNext();
            }
        }
        return null;
    }


}
