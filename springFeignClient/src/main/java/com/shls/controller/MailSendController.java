package com.shls.controller;

import com.shls.service.MailSendService;
import org.apache.http.protocol.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/email")
public class MailSendController {
    @Resource
    MailSendService mailSendService;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public Object sendMail(HttpServletRequest request) {
        mailSendService.sendMail(request);
        return "email发送成功";
    }
}
