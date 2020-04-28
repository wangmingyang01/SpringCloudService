package com.shls.ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  @Configuration注解不能放在@SpringBootApplication所在的包及其子包下
 *
 * */
@Configuration
public class RibbonConfiguration {

    /**
     * 设置负载均衡的规则为随机
     * */
    @Bean
    public IRule ribbonRule() {
        return new RandomRule();
        //return new RoundRobinRule();//轮询
        //return new RetryRule();    //对选定的负载均衡策略机上重试机制。
        //return new BestAvailableRule();  //最大可用策略
    }
}
