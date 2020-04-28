package com.shls.aop.agent.cglib;

import org.springframework.cglib.proxy.Enhancer;

public class main {
    public static void main(String[] args) {
        //创建目标对象
        AccountService target = new AccountService();
        //
        //创建代理对象
        AccountService proxy = (AccountService) Enhancer.create(target.getClass(), new AccountAdvice());
        proxy.transfer();
    }
}
