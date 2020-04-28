package com.shls.handler;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * 自定义拦截器
 */
public class DefineInterceptorHandler implements HandlerInterceptor {
    private Logger logger = Logger.getLogger("DefineInterceptorHandler");

    /**
     *
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url = httpServletRequest.getRequestURL().toString();
        logger.info("路径："+ url +",已经拦截！");

        // 只有返回true才会继续向下执行，返回false取消当前请求
        return true;
    }

    /**
     *
     *请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     *
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     *
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     *
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {


    }
}
