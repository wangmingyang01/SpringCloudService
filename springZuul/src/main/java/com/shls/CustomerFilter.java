package com.shls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 自定义过滤器
 */
@Component
public class CustomerFilter implements Filter, Ordered {
    private static Logger logger = LoggerFactory.getLogger(CustomerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = new String(httpServletRequest.getRequestURI());

        //过滤 /redis 路径
        if (url.startsWith("/redis")) {
            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream out = response.getOutputStream();
            out.write("{\"code\":\"401\",\"message\":\"Filter: 网关过滤的请求\"}".getBytes());
            out.flush();
            return;
        }

        //放行请求
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
