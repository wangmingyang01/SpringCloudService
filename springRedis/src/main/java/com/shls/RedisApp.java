package com.shls;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 1.使用redis(集群)进行缓存
 * http://www.cnblogs.com/shamo89/p/8227559.html
 * 2.单应用事务
 * 3.分布式事务GTS
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient//ribbon
@EnableTransactionManagement//开启事务机制（单应用）
//@MapperScan("com.shls.db.dao")//加载mybatis dao
@EnableCaching//开启缓存
@ImportResource(locations= {"classpath:txc/txc-client-context.xml"})//引入GTS分布式事务
public class RedisApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(RedisApp.class, args);
    }


}
