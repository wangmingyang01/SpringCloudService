package com.shls.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

import static org.springframework.cache.interceptor.DefaultKeyGenerator.NO_PARAM_KEY;
import static org.springframework.cache.interceptor.DefaultKeyGenerator.NULL_PARAM_KEY;

@Configuration
public class CacheConfig {

    /**
     * 设置缓存对象的序列化方式(存储为json格式)
     * 另外对于json序列化,对象要提供默认空构造器
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        //缓存过期时间（秒）
        //cacheManager.setDefaultExpiration(300);
        return cacheManager;
    }


    /**
     * 缓存自定义生成策略
     * 此方式可以解决：多参数、原子类型数组、方法名识别 等
     */
    @Bean(name = {"defaultKeyGenerator"})
    public KeyGenerator defaultKeyGenerator() {
        return new KeyGenerator() {
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder key = new StringBuilder();
                key.append(target.getClass().getSimpleName()).append(".").append(method.getName()).append(":");

                if (params.length == 0) {
                    return key.append(NO_PARAM_KEY).toString();
                }
                for (Object param : params) {
                    if (param == null) {
                        key.append(NULL_PARAM_KEY);
                    } else if (ClassUtils.isPrimitiveArray(param.getClass())) {
                        int length = Array.getLength(param);
                        for (int i = 0; i < length; i++) {
                            key.append(Array.get(param, i));
                            key.append(',');
                        }
                    } else if (ClassUtils.isPrimitiveOrWrapper(param.getClass()) || param instanceof String) {
                        key.append(param);
                    } else {
                        key.append(param.hashCode());
                    }
                    key.append('-');
                }

                return key.toString();
            }
        };
    }

    @Bean(name = {"keyGenerator"})
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder(method.getName() + "_");

                for(int i = 0; i < params.length; ++i) {
                    sb.append(params[i] + "_");
                }

                return sb.toString();
            }
        };
    }

}
