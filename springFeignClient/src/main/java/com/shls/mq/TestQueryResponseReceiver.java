package com.shls.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * test.query.request队列的消息接收者－－－消息消费者
 */
@RabbitListener(queues = "test.query.response")
@Component
public class TestQueryResponseReceiver {
    Logger logger = LoggerFactory.getLogger(MessageQueueSender.class);

    @RabbitHandler
    public void receive(String message) throws IOException {
        logger.info("收到消息:" + message);


        // 解析消息
        /*JSONObject messageMap = JSON.parseObject(message);
        String apiName = (String) messageMap.get("apiName");
        String mess = (String) messageMap.get("message");*/
    }
}
