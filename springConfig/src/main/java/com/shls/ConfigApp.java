package com.shls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 统一配置
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(ConfigApp.class, args);
    }
}
