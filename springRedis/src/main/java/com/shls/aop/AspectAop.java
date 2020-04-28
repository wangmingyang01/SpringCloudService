package com.shls.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * AOP切面切入方法中
 */
@Aspect
@Component
@Order(0)
public class AspectAop {
    Logger logger = Logger.getLogger("aop");

    //定义切面范围
    @Pointcut("execution(public * com.shls.controller.AspectController.*(..))")
    public void webLog(){}

    //环绕通知,环绕增强
    @Around("webLog()")
    public Object around(ProceedingJoinPoint pjp) {
        logger.info("-----Around-1");
        try {
            Object o =  pjp.proceed();
            logger.info("-----Around-2");
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    //方法执行前执行
    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        logger.info("-----Before");
    }

    //后置最终通知,不管是抛出异常或者正常退出都会执行
    @After("webLog()")
    public void after(JoinPoint jp){
        logger.info("-----After");
    }

    //正常执行
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void afterReturning(Object ret) throws Throwable {
        logger.info("-----AfterReturning");
    }

    //异常执行
    @AfterThrowing("webLog()")
    public void afterThrowing(JoinPoint jp){
        logger.info("-----AfterThrowing");
    }


}


