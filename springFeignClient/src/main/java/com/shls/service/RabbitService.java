package com.shls.service;

import com.shls.mq.MessageQueueSender;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 *  spring Bean生命周期的知识点
 *  BeanNameAware, BeanFactoryAware, ApplicationContextAware
 *  bean初始化前调用的接口
 */
@Service
public class RabbitService implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, BeanPostProcessor {
    @Resource
    private MessageQueueSender messageQueueSender;

    public void sendQueueMessage(String message, int order){
        Map map = new HashMap();
        map.put("message", StringUtils.isEmpty(message)?"这是一条测试mq信息":message);
        messageQueueSender.sendTestRequestMessage("test", map, order);
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setBeanName(String name) {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
