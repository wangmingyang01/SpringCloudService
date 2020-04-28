package com.shls.config.security;

import com.shls.utils.SpringContextUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by song on 05/03/2018.
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter
{

    private TokenAuthenticationService tokenAuthenticationService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager)
    {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        tokenAuthenticationService = (TokenAuthenticationService) SpringContextUtil.getBean("tokenAuthenticationService");

        Authentication authentication;
        try
        {
            authentication = tokenAuthenticationService.getAuthentication(request);
        } catch (Exception e)
        {
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.getWriter().print("{\"code\":\"401\",\"message\":\"unauthenticated\"}");
            return;
        }

        if (authentication == null)
        {
            chain.doFilter(request, response);
            return;
        }


        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }
}