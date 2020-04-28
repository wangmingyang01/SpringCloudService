package com.shls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;

/**
 * 1.分布式事务GTS(子服务)
 */
@SpringBootApplication
@EnableDiscoveryClient
@ImportResource(locations= {"classpath:txc/txc-client-context.xml"})//引入GTS分布式事务
public class RedisTxcApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(RedisTxcApp.class, args);
    }

}
