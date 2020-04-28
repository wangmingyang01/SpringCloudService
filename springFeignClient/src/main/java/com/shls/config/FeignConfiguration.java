package com.shls.config;

import feign.Contract;
import feign.codec.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign重写编码器支持pojos多实体与文件数组参数传递的方法
 *
 * 接口中使用@RequestLine标记请求路径，使用@Param注解标记每一个请求参数
 **/
//@Configuration
public class FeignConfiguration {

    // 启用Fegin自定义注解 如@RequestLine @Param
    @Bean
    public Contract feignContract(){
        return new Contract.Default();
    }


    //feign 实现多pojo传输与MultipartFile上传 编码器，需配合开启feign自带注解使用
    @Bean
    public Encoder feignSpringFormEncoder(){
        return new FeignSpringFormEncoder();
    }
}
