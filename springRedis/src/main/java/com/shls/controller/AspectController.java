package com.shls.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 * aop
 */
@RestController
@RequestMapping("/aop")
public class AspectController {
    Logger logger = Logger.getLogger("AspectController");

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Object test(){
        logger.info("AOP执行方法！");

        return "aop测试完毕！";
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Object Post(String lasttime){
        logger.info("已经接收到请求"+lasttime);

        return "{\n" +
                "    \"code\": 401,\n" +
                "    \"message\": \"unauthorized\"\n" +
                "}";
    }
}
