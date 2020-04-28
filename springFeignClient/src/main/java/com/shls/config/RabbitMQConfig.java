package com.shls.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by song on 08/12/2017.
 */
@Configuration
public class RabbitMQConfig
{
    /**
     * 查询队列名称
     */
    String testQueryRequestQueueName = "test.query.request";


    /**
     * 创建bean
     */
    @Bean(name ="testQueryRequestQueue")
    Queue testQueryRequestQueue()
    {
        //设置队列优先级
        Map<String, Object> args= new HashMap<>();
        args.put("x-max-priority", 100);
        //durable=true　队列持久化
        return new Queue(testQueryRequestQueueName, true, false, false, args);
    }


}
