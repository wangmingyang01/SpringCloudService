package com.shls.service;

import com.shls.utils.AESUtils;
import com.shls.utils.MailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@Service
public class MailSendService {
    @Resource
    MailSender mailSender;

    public void sendMail(HttpServletRequest request) {
        try {
            //ip
            System.out.println(getRequestUrl(request));
            String sid = "wang,"+System.currentTimeMillis() +","+ AESUtils.randomStr(6);

            StringBuilder body = new StringBuilder();
            body.append("尊敬的用户：<br/>");
            body.append("<P style=\"text-indent:2em;\">")
                    .append("您申请的重置密码请求已生成，如需继续下一步操作请点击以下链接")
                    .append("<br/><br/>")
                    .append("https://127.0.0.1/test")
                    .append("?userName=wang")
                    .append("&sid=").append(AESUtils.encrypt(sid))
                    .append("</P>");

            body.append("<br/>")
                    .append("<span style=\"font-size:12px;color:red;\">")
                    .append("注：为了您帐号安全，请勿将此链接涉露他人")
                    .append("</span>");


            mailSender.send(Arrays.asList("1279476881@qq.com"), "重置密码申请", body.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取真实IP
     * @param request 请求体
     * @return 真实IP
     */
    public String getRealIp(HttpServletRequest request) {
        // 这个一般是Nginx反向代理设置的参数
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 处理多IP的情况（只取第一个IP）
        if (ip != null && ip.contains(",")) {
            String[] ipArray = ip.split(",");
            ip = ipArray[0];
        }
        return ip;
    }

    /**
     * // 获取HTTP请求的上下文绝对路径（完整的协议名+主机+端口号）
     */
    public String getRequestUrl(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL(); // http://127.0.0.1:14530/siteNews/add
        String contextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
        if (contextUrl.endsWith("/")) {
            contextUrl = contextUrl.substring(0, contextUrl.length() - 1);
        }

        // http://127.0.0.1:14530
        return contextUrl;
    }
}
