package com.shls.webSocket;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 下载附件文件异常关闭webSocket
 */
@Aspect
@Component
public class webSocketExceptionAop {
    @Pointcut("execution(public * com.shls.controller.WebSocketController.downloadZipFile(..))")
    public void webSocket(){}

    @AfterThrowing("webSocket()")
    public void closeWebSocket(JoinPoint jp){
        //获取参数值
        Object[] args = jp.getArgs();
        String downloadCode = (String) args[0];
        System.out.println("方法异常时执行.....:"+args[0]);
    }
}
