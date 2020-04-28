package com.shls.controller;

import com.shls.config.LoggedUser;
import com.shls.service.IndexService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.logging.Logger;

/**
 * 登录
 */
@RestController
@RequestMapping("/auth")
public class IndexController {
    Logger logger = Logger.getLogger("IndexController");

    @Resource
    IndexService indexService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestParam(value = "account") String account,
                        @RequestParam(value = "password") String password){
       String token = indexService.login(account, password);

        return new ResponseBuilder().success().add("account", account).add("token", token).build();
    }
}
