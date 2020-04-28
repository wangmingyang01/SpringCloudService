package com.shls.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.shls.config.security.LoggedUser.*;

/**
 * Created by song on 11/02/2018.
 */
@Component
public class TokenAuthenticationService
{
    static final String HEADER_STRING = "Authorization";// 存放Token的Header Key

    @Resource
    JwtTokenHelper jwtTokenHelper;

    // JWT验证方法
    public Authentication getAuthentication(HttpServletRequest request)
    {
        // 从Header中拿到token
        String token = request.getHeader(HEADER_STRING);

        if (token != null)
        {
            try
            {
                com.shls.config.security.LoggedUser lu = jwtTokenHelper.parseToken(token);
                List<GrantedAuthority> authorities = new ArrayList<>();
                // 获取权限
                authorities = appendPermissions(authorities);
                lu.setPermissions(authorities);

                // 返回验证令牌
                return lu != null ?
                        new UsernamePasswordAuthenticationToken(lu, null, authorities) :
                        null;
            } catch (Exception e)
            {
                throw new RuntimeException("token解析验证失败");
            }

        }
        return null;
    }

    /**
     * 添加 当前用户的权限
     *
     * @param authorities
     */
    private List<GrantedAuthority> appendPermissions(List<GrantedAuthority> authorities)
    {
        List<GrantedAuthority> auths = new ArrayList<>();
        Set<String> permissions = new HashSet(){{
            add("客户经理");
            add("单证专员");
        }};


        for (String permission : permissions)
        {
            auths.add(new SimpleGrantedAuthority("ROLE_" + permission));
        }

        return auths;
    }


}