package com.shls.controller;

import com.google.gson.Gson;
import com.shls.db.po.User;
import com.shls.utils.JsonUtil;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.HashMap;

@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * HashMap转化为实体bean
     */
    @RequestMapping(value = "/hashMapToBean", method = RequestMethod.POST)
    public Object hashMapToBean(@RequestParam HashMap<String, Object> data, MultipartFile file) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(data);
        User user = gson.fromJson(jsonStr, User.class);

        if (file != null) {
            System.out.println("不为空");
        } else {
            System.out.println("空");
        }

        return new ResponseBuilder().success().add("user", user).build();
    }

    /**
     * 接收json
     */
    @RequestMapping(value = "/receiveJson", method = RequestMethod.POST)
    public Object receiveJson(@RequestBody String data, HttpServletRequest request) {
        System.out.println(request.getHeader("channelNo"));
        System.out.println(data);

        return new ResponseBuilder().success().build();
    }

    @RequestMapping(value = "/toReturn", method = RequestMethod.GET)
    public Object toReturn() {
        return "test测试";
    }
}
