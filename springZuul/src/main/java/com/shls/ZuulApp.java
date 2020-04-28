package com.shls;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 动态路由
 */
@EnableZuulProxy
@SpringCloudApplication
public class ZuulApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(ZuulApp.class, args);
    }
}
