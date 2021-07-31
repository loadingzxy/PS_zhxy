package com.example.eventbus.demo.util.chain;

import org.apache.commons.collections4.MapUtils;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @name: event_bus_demo
 * @description: 责任链注解任务处理类
 * @author: zhxy
 * @create: 2021/7/31 20:54
 **/
@Component
//public class TaskChainAnnoProcessor extends InitDestroyAnnotationBeanPostProcessor implements ApplicationContextAware {

public class TaskChainAnnoProcessor implements ApplicationContextAware, BeanPostProcessor {


    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        try {

            if (bean instanceof BaseTask) {
                processTaskChain((BaseTask) bean, beanName);
            }
        } catch (Exception e) {
            throw new BeanInitializationException(
                    "Could not recognize baseCommand while processing bean:" + beanName, e
            );
        }
        return bean;
    }

    private void processTaskChain(BaseTask baseTask, String beanName) throws Exception {
        Class<?> cls = getBeanClass(baseTask);
        TaskChain anno = null;

        while (anno == null && cls != null) {
            anno = cls.getAnnotation(TaskChain.class);
            cls = cls.getSuperclass();
        }

        if (anno != null) {
            //获取执行链上的beanId
            String[] commandNames = anno.commands();

            List<CommandWrap<?>> commands = new ArrayList<>();
            Map<String, CommandWrap<?>> commandMap = new HashMap<>();

            if (commandNames != null && commandNames.length > 0) {
                for (int i = 0; i < commandNames.length; i++) {
                    String commandName = commandNames[i];
                    BaseCommand command = (BaseCommand<?>) applicationContext.getBean(commandName);

                    if (command != null) {
                        command.getConfig().setBeanName(commandName);
                        CommandWrap commandWrap = new CommandWrap();
                        commandWrap.setBaseCommand(command);
                        if (i > 0) {
                            commands.get(i - 1).setNext(command);
                        }
                        commands.add(commandWrap);
                        commandMap.put(commandName, commandWrap);
                    }

                }
            }

            if (!commands.isEmpty()) {
                baseTask.setCommands(commands);
            }
            if (MapUtils.isNotEmpty(commandMap)) {
                baseTask.setCommandMap(commandMap);
            }
        }
    }

    private Class<?> getBeanClass(Object bean) throws Exception {
        Class<?> cls;
        if (AopUtils.isJdkDynamicProxy(bean)) {
            cls = ((Advised) bean).getTargetSource().getTarget().getClass();
        } else {
            cls = bean.getClass();
        }
        return cls;
    }


}
