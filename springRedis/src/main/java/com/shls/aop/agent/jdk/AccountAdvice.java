package com.shls.aop.agent.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


//JDK动态代理实现InvocationHandler接口
public class AccountAdvice implements InvocationHandler {
    //目标对象
    private IAccountService target;

    public AccountAdvice(IAccountService target) {
        this.target = target;
    }

    /**
     * 代理方法, 每次调用目标方法时都会进到这里
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result =  method.invoke(target, args);
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
