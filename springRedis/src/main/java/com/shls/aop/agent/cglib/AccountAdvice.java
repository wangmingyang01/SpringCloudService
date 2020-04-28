package com.shls.aop.agent.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

//Cglib动态代理，实现MethodInterceptor接口
public class AccountAdvice implements MethodInterceptor {
    /**
     * 代理方法, 每次调用目标方法时都会进到这里
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        before();
        Object result =  methodProxy.invokeSuper(obj, args);
        after();
        return result;
    }

    /**
     * 前置增强
     */
    private void before() {
        System.out.println("对转账进行前置增强操作---before()");
    }

    /**
     * 后置增强
     */
    private void after() {
        System.out.println("对转账进行后置增强操作---after()");
    }
}
