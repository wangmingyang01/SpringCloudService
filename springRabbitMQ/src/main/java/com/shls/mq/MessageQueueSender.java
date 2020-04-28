package com.shls.mq;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * 发送消息给消息订阅者
 */
@Component
public class MessageQueueSender
{
    Logger logger = LoggerFactory.getLogger(MessageQueueSender.class);

    @Resource(name="testQueryResponseQueue")
    Queue testQueryResponseQueue;

    private final RabbitTemplate rabbitTemplate;
    public MessageQueueSender(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }

    private void send(Queue queue, String apiName, Map<String, Object> requestParams)
    {
        Map<String, Object> map = new HashMap();
        map.put("apiName", apiName);
        if (requestParams != null)
        {
            map.putAll(requestParams);
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        rabbitTemplate.convertAndSend(queue.getName(), jsonObject.toString());
        logger.info("发送消息:{}",jsonObject.toString());
    }

    /**
     * 发送消息
     * @param apiName 接口名称
     */
    public void sendTestResponseMessage(String apiName, Map<String, Object> requestParams)
    {
        // 打开请求
        send(testQueryResponseQueue, apiName, requestParams);

    }



}
