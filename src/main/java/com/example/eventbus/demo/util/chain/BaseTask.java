package com.example.eventbus.demo.util.chain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: event_bus_demo
 * @description: 责任链基础任务类
 * @author: zhxy
 * @create: 2021-05-05 15:11
 **/
@Slf4j
public abstract class BaseTask<T extends ContextObject> {

    @Getter
    @Setter
    private List<CommandWrap<T>> commands = new ArrayList<>();

    @Getter
    @Setter
    private Map<String,CommandWrap<T>> commandMap = new HashMap<>();

    public final void execute(T entity) throws Exception{
        this.execute(null,entity);
    }

    public final void process(T entity){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try{
            this.prepare(entity);
            beforeExecute(entity);
            int cmdSize = commands.size();
            if(cmdSize < 1 ){
                return;
            }
            CommandWrap<T> commandWrap = commands.get(0);
            if(null == commandWrap){
                return;
            }

            BaseCommand<T> baseCommand = commandWrap.getBaseCommand();
            if(null == baseCommand){
                return;
            }

            baseCommand.process(entity);

            afterExecute(entity);
        }catch (Exception e){
            throw e;
        }
        stopWatch.stop();
        log.info("[taskProcess][spendTime][{}]",stopWatch.getTotalTimeMillis());
    }

    protected abstract void afterExecute(T entity);

    protected abstract void beforeExecute(T entity);


    protected final void execute(String commandName, T entity)throws Exception{
        try{
            this.prepare(entity);

            beforeExecute(entity);
            BaseCommand<T> tBaseCommand = null;

            for (int i = 0; i < commands.size(); i++) {
                long beforeTime = System.currentTimeMillis();
                boolean success = commands.get(i).getBaseCommand().excute(entity);
                long afterTime = System.currentTimeMillis();
                log.info("[{}][cost time]",commands.get(i).getBaseCommand().getClass().getSimpleName(),(afterTime-beforeTime)/1000);
                if(!success){
                    log.warn("[error command][{}]",commands.get(i).getBaseCommand().getClass().getSimpleName());
                }
            }
            afterExecute(entity);
        }catch (Exception e){
            throw e;
        }
    }

    private void prepare(T entity){
        ContextObject contextObject = (ContextObject) entity;

        contextObject.commandMap = this.commandMap;

        contextObject.initCommandParamMap();
    }
}
