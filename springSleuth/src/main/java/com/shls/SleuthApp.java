package com.shls;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.internal.EnableZipkinServer;

/**
 * 分布式服务链路追踪
 */

@SpringBootApplication
@EnableZipkinServer
public class SleuthApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(SleuthApp.class, args);
    }
}
