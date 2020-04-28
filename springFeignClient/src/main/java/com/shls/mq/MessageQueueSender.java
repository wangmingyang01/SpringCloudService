package com.shls.mq;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * 发送消息给消息订阅者---消息生产者
 */
@Component
public class MessageQueueSender
{
    Logger logger = LoggerFactory.getLogger(MessageQueueSender.class);

    @Resource(name="testQueryRequestQueue")
    Queue testQueryRequestQueue;

    private final RabbitTemplate rabbitTemplate;
    public MessageQueueSender(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }

    private void send(Queue queue, String apiName, Map<String, Object> requestParams, int order)
    {
        Map<String, Object> map = new HashMap();
        map.put("apiName", apiName + order);
        if (requestParams != null)
        {
            map.putAll(requestParams);
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        rabbitTemplate.convertAndSend(queue.getName(), (Object) jsonObject.toString(), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message arg0) throws AmqpException
            {
                arg0.getMessageProperties().setPriority(order);
                return arg0;
            }
        });
        logger.info("发送消息:{}",jsonObject.toString());
    }

    /**
     * 发送消息
     * @param apiName 接口名称
     * @param order 消息优先级
     */
    public void sendTestRequestMessage(String apiName, Map<String, Object> requestParams, int order)
    {
        // 打开请求
        send(testQueryRequestQueue, apiName, requestParams, order);
    }



}
