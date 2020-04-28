package com.shls.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by song on 08/12/2017.
 */
@Configuration
public class RabbitMQConfig
{
    /**
     * 查询队列名称
     */
    String testQueryResponseQueueName = "test.query.response";


    /**
     * 创建bean
     */
    @Bean(name ="testQueryResponseQueue")
    Queue testQueryResponseQueue()
    {
        return new Queue(testQueryResponseQueueName, true);
    }


}
