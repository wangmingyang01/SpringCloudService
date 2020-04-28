package com.shls.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * test.query.request队列的消息接收者
 */
@RabbitListener(queues = "test.query.request")
@Component
public class TestQueryRequestReceiver {
    Logger logger = LoggerFactory.getLogger(MessageQueueSender.class);

    @Resource
    private MessageQueueSender messageQueueSender;

    @RabbitHandler
    public void receive(String message) {
        logger.info("收到消息:" + message);

        // 解析消息
        JSONObject messageMap = JSON.parseObject(message);
        String apiName = (String) messageMap.get("apiName");
        String mess = (String) messageMap.get("message");

        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("apiName", apiName);
        requestParams.put("message", "收到了消息，这是返回的信息");
        //返回
        messageQueueSender.sendTestResponseMessage(apiName, requestParams);
    }
}
