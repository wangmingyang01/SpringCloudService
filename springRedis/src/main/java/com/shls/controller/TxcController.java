package com.shls.controller;

import com.shls.db.po.User;
import com.shls.db.service.UserService;
import com.taobao.txc.client.aop.annotation.TxcTransaction;
import com.taobao.txc.common.TxcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 分布式事务（阿里GTS）
 */
@RestController
@RequestMapping("/txc")
public class TxcController {
    @Resource
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;

    //操作同一个数据库回滚
    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    @TxcTransaction(timeout = 60000)
    public Object insert() {
        User userNew = new User();
        userNew.setId(3);
        userNew.setName("李四");
        userService.insert(userNew);

        //重复id,回滚
        User user = new User();
        user.setId(2);
        user.setName("张三");
        userService.insert(user);

        return "分布式事务GTS测试结束！";
    }


    /**
     * 任何一个调用服务出错,所有服务数据回滚
     * @TxcTransactiona 开启分布式事务，调用下游子服务不需要再开启
     *
     * 服务 A 调用服务 B，服务 A 上加了 GTS 开启事务注解，A 和 B 在一个事务中。
     * 那么这个客户端 A 注解函数下面所有的被调用的服务及其子调用的服务都会加入到这个客户端 A 的事务中
     */
    @RequestMapping(value = "/insertTxc", method = RequestMethod.GET)
    @TxcTransaction(timeout = 60000)
    public Object insertTxc(){
        String xid = TxcContext.getCurrentXid();
        TxcContext.bind(xid,null);

        //插入数据成功
        User userNew = new User();
        userNew.setId(7);
        userNew.setName("伍六一");
        userService.insert(userNew);

        Integer.valueOf("1pppp");

        // 重复id,回滚上一条数据
        // [ribbon方式]将原来的ip:port的形式，改成注册到Eureka Server上的应用名即可
        //restTemplate.getForObject("http://springRedisTxc/txc/ribbon?xid=" + xid  , String.class);

        TxcContext.unbind();
        return "springRedis-insertTxc():分布式事务GTS结束！";
    }

}
