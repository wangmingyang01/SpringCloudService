package com.shls.controller;

import com.shls.service.RabbitService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mq")
public class RabbitMQController {
    @Resource
    RabbitService rabbitService;

    @RequestMapping(value = "/rabbitmq", method = RequestMethod.GET)
    public Object Rabbitmq(@RequestParam(name = "message", required = false) String message,
                           @RequestParam(name = "order", defaultValue = "0") int order) {
        rabbitService.sendQueueMessage(message, order);
        return "MQ测试成功";
    }

}
