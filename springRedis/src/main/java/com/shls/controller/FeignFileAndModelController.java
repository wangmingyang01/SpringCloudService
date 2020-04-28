package com.shls.controller;

import com.shls.vo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 测试
 * Feign重写编码器支持pojos多实体与文件数组参数传递的方法
 */
@RestController
@RequestMapping("/fileAndModel")
public class FeignFileAndModelController {

    @RequestMapping("/hello")
    public String index(@RequestParam(value = "name")String name) {
        return "hello "+name+"，this is first messge";
    }

    @RequestMapping(value = "/hello3")
    public String index3(
            @RequestPart(value = "name", required = false) String name,
            @RequestPart(value = "number", required = false) Integer number,
            @RequestPart(value = "date", required = false) Date date,
            @RequestPart(value = "user", required = false) User user,
            @RequestPart(value = "file1", required = false) MultipartFile file1,
            @RequestPart(value = "files", required = false) MultipartFile[] files
    ) {
        String result = "hello3成功进入生产者 \n";
        result += " name: " + name;
        result += " number: " + number;
        result += " \n ------------ " + date;
        result += " \n ------------" + user.toString();
        result += " \n ------------ " + file1.getOriginalFilename();
        result += " \n ------------ " + files.length;
        return result;
    }
}
