package com.shls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 登录系统，生成token
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AuthenticationApp {
    public static void main(String[] args)
    {
        SpringApplication.run(AuthenticationApp.class, args);
    }
}
