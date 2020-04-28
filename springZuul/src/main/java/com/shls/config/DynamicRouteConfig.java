package com.shls.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 动态路由配置s
 */
@Configuration
public class DynamicRouteConfig {

    //是否启用动态路由
    @Value("${application.routes.dynamic.enabled}")
    private boolean enabledDynamicRoute;

    /**
     * 使用自定义的路由策略代替默认路由策略
     */
    @Bean
    public SimpleRouteLocator routeLocator(ZuulProperties zuulProperties) {
        return new DynamicRouteLocator(enabledDynamicRoute, zuulProperties.getPrefix(), zuulProperties);
    }
}
