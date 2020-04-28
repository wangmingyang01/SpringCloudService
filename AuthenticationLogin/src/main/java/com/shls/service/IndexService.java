package com.shls.service;

import com.shls.Service.ServiceException;
import com.shls.config.JwtTokenHelper;
import com.shls.config.LoggedUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IndexService {
    @Resource
    JwtTokenHelper jwtTokenHelper;

    public String login(String account, String password) {
        //验证帐号密码
        if(account.equals(password)){
            throw new ServiceException("登录帐号和密码不正确");
        }

        String token = null;
        try{
            token = jwtTokenHelper.createToken(new LoggedUser(1l, account, "CLIENT", null));
        }catch (Exception e){
            throw new ServiceException("验证帐号和密码异常：" + e.getMessage());
        }

        return token;
    }
}
