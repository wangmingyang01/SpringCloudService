package com.shls.controller;

import com.shls.feign.BgyInterfaceServiceCaller;
import com.shls.feign.SpringRedisServiceCaller;
import com.shls.feign.vo.*;
import com.shls.feign.vo.order.WordOrderAttachmentVo;
import com.shls.utils.StringUtil;
import com.shls.webSocket.WebSocketClientSend;
import com.shls.webSocket.WebSocketService;
import com.shls.webSocket.user.WebSocketToUserService;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.shls.feign.SpringSwaggerServer;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import javax.xml.ws.WebServiceClient;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/feign")
public class FeignController {
    @Resource
    SpringSwaggerServer springSwaggerServer;
    @Resource
    BgyInterfaceServiceCaller bgyInterfaceServiceCaller;
    @Resource
    SpringRedisServiceCaller springRedisServiceCaller;
    @Resource
    RedisTemplate redisTemplate;

    //
    @RequestMapping(value = "/redis", method = RequestMethod.GET)
    @Scope(value = "prototype")//singleton单例（默认），prototype原型
    public Object object(){
        redisTemplate.opsForHash().put("userNo", "userNo_110", "dddddd");
        return true;
    }

    @RequestMapping(value = "/object", method = RequestMethod.GET)
    public Object object(Integer id, HttpSession session){

       return springSwaggerServer.object(id);
    }

    @RequestMapping(value = "/feignServer", method = RequestMethod.GET)
    public Object feignServer(){

        return springSwaggerServer.feignServer("name");
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getUser(){
        User user = new User(1, "许三多");
        return springSwaggerServer.getUser(user);
    }

    //服务间调用复杂对象（feign）
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Object PostSwagger(@PathVariable Integer id){
        User user = new User(id, "许三多");
        //集合List<Score> scores
        Score s1 = new Score();
        s1.setCourse("语文");
        s1.setNum(90);
        Score s2 = new Score();
        s2.setCourse("数学");
        s2.setNum(100);
        user.setScores(Arrays.asList(s1,s2));

        return springSwaggerServer.getUser(user);
    }



    /**
     * 接口4 推送付款单融资审核结果
     */

    @RequestMapping(value = "/sendAuditingResultInfo", method = RequestMethod.GET)
    public Object sendAuditingResultInfo(){

        Auditingresultinfo ao = new Auditingresultinfo();
        ao.setBgyPaymentUuid("B84D468E-455A-E811-80C0-005056848217");
        ao.setBgyPaymentNo("D32018061200076403");
        ao.setQhyfResult(1);
        ao.setQhyfChange(1);
        //发票
        SendInvoiceInfo in1 = new SendInvoiceInfo();
        in1.setQhyfInvoiceCode("");
        in1.setQhyfInvoiceNo("50652112");
        in1.setQhyfInvoiceAmount(60.732);
        in1.setQhyfInvoiceDate("2018-02-11");
        SendInvoiceInfo in2 = new SendInvoiceInfo();
        in2.setQhyfInvoiceCode("");
        in2.setQhyfInvoiceNo("12342112");
        in2.setQhyfInvoiceAmount(60.756);
        ao.setInvoiceInfo(Arrays.asList(in1,in2));
        return bgyInterfaceServiceCaller.sendAuditingResultInfo(Arrays.asList(ao));

    }

    @RequestMapping(value = "/sendFinancingBatchInfo", method = RequestMethod.GET)
    public Object sendFinancingBatchInfo(){
        FinancingBatchInfo f1 = new FinancingBatchInfo();
        f1.setBankaccount("33001638063052500226");
        f1.setBankname("碧桂园-和信供应链金融第一期资产支持专项计划");
        f1.setCompany("和信(天津)国际保理公司");
        f1.setBatchno("1");
        f1.setExpirydate("2018-07-01");
        f1.setPeriods("1");
        f1.setSourcesys(null);

        FinancingBatchInfo f2 = new FinancingBatchInfo();
        f2.setBankaccount("6225834823540042");
        f2.setBankname("碧桂园【1-10】期供应链金融资产支持专项计划35亿");
        f2.setCompany("和信(天津)国际保理公司");
        f2.setBatchno("1");
        f2.setExpirydate("2018-07-03");
        f2.setPeriods("2");
        f2.setSourcesys("");

        return bgyInterfaceServiceCaller.sendFinancingBatchInfo(Arrays.asList(f1,f2));
    }

    @RequestMapping(value = "/sendAssetPackage", method = RequestMethod.GET)
    public Object sendAssetPackage(){
        AssetPackage a = new AssetPackage();
        a.setBatchno("1");
        a.setBusinessCode("18");
        a.setBusinessName("ABS");
        a.setDrName("和信(天津)国际保理");
        a.setPeriod("QS001");
        a.setAmount("79.57");
        a.setUuids(Arrays.asList(new Uuids("2A3453CD-2F75-E811-80C3-005056848217"),new Uuids("6E582117-455A-E811-80C0-005056848217")));

        return bgyInterfaceServiceCaller.sendAssetPackage(a);
    }

    /**
     * 带附件表单的feign传递
     */
    @RequestMapping(value = "/uploadWordOrderAttachment", method = RequestMethod.POST)
    public Object uploadWordOrderAttachment(@ModelAttribute @Valid WordOrderAttachmentVo vo){
        return springSwaggerServer.uploadWordOrderAttachment(vo);
    }

    /**
     * Feign重写编码器支持pojos多实体与文件数组参数传递的方法
     */
    /*@RequestMapping(value = "/fileAndModel/hello3", method = RequestMethod.POST)
    public Object fileAndModelHello3(@RequestPart(value = "name", required = false) String name,
                                    @RequestPart(value = "number", required = false) Integer number,
                                    @RequestPart(value = "date", required = false) Date date,
                                    @RequestPart(value = "user", required = false) User user,
                                    @RequestPart(value = "file1", required = false) MultipartFile file1,
                                    @RequestPart(value = "files", required = false) MultipartFile[] files){
        return springRedisServiceCaller.hello3(name, number, date, user, file1, files);
    }*/

}
