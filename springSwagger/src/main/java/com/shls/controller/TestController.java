package com.shls.controller;

import com.shls.utils.JsonUtil;
import com.shls.vo.User;
import com.shls.vo.order.WordOrderAttachmentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/swagger")
@Api(value = "/swagger", description = "测试模块")
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ApiOperation(value = "测试Api", httpMethod = "GET", notes = "详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer", paramType = "query")
    })
    public Object test(Integer id,HttpSession session){

        return "访问springSwagger--10005,sessionID:"+session.getId();
    }


    @RequestMapping(value = "/feignServer", method = RequestMethod.POST)
    @ApiOperation(value = "feign服务器", httpMethod = "POST", notes = "feign服务器详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "name", required = true, dataType = "string", paramType = "query")
    })
    public String feignServer(String name){

        return "访问feignServer()接口!";
    }

    @RequestMapping(value = "/object", method = RequestMethod.GET)
    @ApiOperation(value = "返回Object类型", httpMethod = "GET", notes = "返回Object类型")
    public Object object(int id){
        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取session
        HttpSession session = request.getSession();

        Map map = new HashMap();
        map.put("id",id);
        map.put("sessionID",session.getId());
        map.put("name","访问object()接口！");
        return JsonUtil.jsonConfig(map,null);
    }

    //ribbon请求
    @RequestMapping(value = "/ribbon/user", method = RequestMethod.POST)
    @ApiOperation(value = "返回对象类型", httpMethod = "POST", notes = "返回对象类型")
    public User RibbonUser(@ModelAttribute User user){
        return user;
    }

    //feign请求
    @RequestMapping(value = "/feign/user", method = RequestMethod.POST)
    @ApiOperation(value = "返回对象类型", httpMethod = "POST", notes = "返回对象类型")
    public User FeignUser(@RequestBody User user){
        return user;
    }

    /**
     * 上传工单附件
     */
    @RequestMapping(value = "/uploadWordOrderAttachment", method = RequestMethod.POST)
    @ApiOperation(value = "上传附件并提交工单", httpMethod = "POST", notes = "上传附件并提交工单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "工单id", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "attachmentList[index].file", value = "附件", required = true, dataType = "MultipartFile", paramType = "save"),
            @ApiImplicitParam(name = "attachmentList[index].type", value = "附件类型,中文", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tradingNotesList[index].tradingNotesId", value = "关联的票据Id, 0表示只新增票据", required = false, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tradingNotesList[index].file", value = "票据附件文件", required = true, dataType = "MultipartFile", paramType = "save"),
            @ApiImplicitParam(name = "tradingNotesList[index].type", value = "附件类型,中文", required = false, dataType = "long", paramType = "query")
    })
    public Object uploadWordOrderAttachment(@ModelAttribute @Valid WordOrderAttachmentVo vo) {
        System.out.println("接收到的工单号:"+vo.getOrderId());

        for (int i=0; vo.getAttachmentList()!=null && i<vo.getAttachmentList().size(); i++) {
            System.out.println("其它文件名称：" + vo.getAttachmentList().get(i).getFile().getOriginalFilename()
                    + " " + vo.getAttachmentList().get(i).getType());
        }
        for (int i=0; vo.getTradingNotesList()!=null && i<vo.getTradingNotesList().size(); i++) {
            System.out.println("发票文件名称：" + vo.getTradingNotesList().get(i).getFile().getOriginalFilename()
                    + " " + vo.getTradingNotesList().get(i).getTradingNotesId()
                    + " " + vo.getTradingNotesList().get(i).getType());
        }

        /*//保存附件
        MultipartFile file = wordOrderAttachmentVo.getAttachmentList().get(0).getFile();
        String filePath = "/home/wang/Desktop/包钢/dd/" + file.getOriginalFilename();
        File desFile = new File(filePath);
        System.out.println(desFile.getParentFile().getPath());
        if(!desFile.getParentFile().exists()){
            desFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(desFile);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }*/

        return new ResponseBuilder().success().build();
    }

}
