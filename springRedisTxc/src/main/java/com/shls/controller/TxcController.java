package com.shls.controller;

import com.shls.db.po.User;
import com.shls.db.service.UserService;
import com.taobao.txc.common.TxcContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
@RequestMapping("/txc")
public class TxcController {
    @Resource
    private UserService userService;

    /**
     * springRedis-ribbon方式调用
     * 任何一个调用服务出错,所有服务数据回滚
     * 子服务不需要注解@TxcTransaction
     */
    @RequestMapping(value = "/ribbon", method = RequestMethod.GET)
    public Object ribbon(String xid) {
        TxcContext.bind(xid, null);

        System.out.println(">>>>>>>>>>>xid=" + xid);
        //不要用阿里提供的数据源，GTS容易超时
        /*ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("txc/txc-client-context.xml");
        JdbcTemplate jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
        //id重复,报错回滚
        jdbcTemplate.update("insert into user(id,name) values(4,'李四')");*/


        //插入数据成功
        User user = new User();
        user.setId(4);
        user.setName("李四");
        userService.insert(user);

        //报错目的使主调用服务数据回滚
        //int i = Integer.valueOf("1pppp");

        TxcContext.unbind();
        return "springRedisTxc-ribbon():分布式事务GTS结束！";
    }

}
