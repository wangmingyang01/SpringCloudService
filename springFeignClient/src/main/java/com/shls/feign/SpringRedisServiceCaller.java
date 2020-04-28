package com.shls.feign;

import com.shls.config.FeignConfiguration;
import com.shls.feign.vo.User;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @RequestLine 和　@RequestMapping　同时存在会报错，服务没法启动
 */

@Service
@FeignClient(name = "springRedis"
        /*,fallback = SpringSwaggerServerHystrix.class
        ,url = "127.0.0.1:8080"*/
        ,configuration = FeignConfiguration.class)
public interface SpringRedisServiceCaller {

    /*@RequestLine(value = "POST /fileAndModel/hello2")
    public String hello2(@Param(value = "name") String name);

    @RequestLine(value = "POST /fileAndModel/hello3")
    public String hello3(
            @Param(value = "name") String name,
            @Param(value = "number2") Integer number,
            @Param(value = "date") Date date,
            @Param(value = "user") User user,
            @Param(value = "file1") MultipartFile file1,
            @Param(value = "files") MultipartFile[] files
    );*/
}
