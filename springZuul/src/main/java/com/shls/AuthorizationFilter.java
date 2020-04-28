package com.shls;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 网关过滤器
 */
@Component
public class AuthorizationFilter extends ZuulFilter
{
    private static Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

    //可以在请求被路由之前调用
    @Override
    public String filterType()
    {
        return "pre";
    }

    //优先级，越小优先级越高
    @Override
    public int filterOrder()
    {
        return 0;
    }

    @Override
    public boolean shouldFilter()
    {
        return true;
    }

    @Override
    public Object run()
    {
        RequestContext context = RequestContext.getCurrentContext();
        String uri = context.getRequest().getRequestURI();

        // 如果是访问redis, 则校验
        if (uri.startsWith("/redis")) {
            try {
                checkTokenProvided(context);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;
    }

    private void checkTokenProvided(RequestContext context) throws IOException
    {
        String token = context.getRequest().getHeader("Authorization");
        if (token == null)
        {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
            context.getResponse().setHeader("Content-Type", "application/json;charset=UTF-8");
            context.getResponse().getWriter().print("{\"code\":\"401\",\"message\":\"Zuul: 网关过滤的请求\"}");
        }
    }

}