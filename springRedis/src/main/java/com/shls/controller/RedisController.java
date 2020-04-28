package com.shls.controller;

import com.shls.db.po.User;
import com.shls.db.service.UserService;
import com.shls.utils.JsonUtil;
import net.sf.json.JSONArray;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * redis缓存
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "sel/{id}", method = RequestMethod.GET)
    public Object findUser(@PathVariable long id){
        User user = userService.findById(id);
        if(user == null){
            return "不存在的用户ID:"+id;
        }
        return JsonUtil.jsonConfig(user,null);
    }

    @RequestMapping(value = "selByIds", method = RequestMethod.GET)
    public Object findByIds(@RequestParam(required = false, value = "ids[]")long[] ids){
        List<Long> idsList = new ArrayList<>();
        for (Long id : ids){
            idsList.add(id);
        }
        List<User> users = userService.findByIds(idsList);

        return JsonUtil.jsonConfig(users,null);
    }

    @RequestMapping(value = "sel/{id}/{code}", method = RequestMethod.GET)
    public Object findUser(@PathVariable long id, @PathVariable String code){
        User user = userService.findByIdAndName(id, code);
        if(user == null){
            return "不存在的用户ID:"+id;
        }
        return JsonUtil.jsonConfig(user,null);
    }

    @RequestMapping(value = "findByIdAndNameByMap", method = RequestMethod.GET)
    public Object findByIdAndNameByMap(@RequestParam HashMap<String, Object> params){
        User user = userService.findByIdAndNameByMap(params);
        if(user == null){
            return "不存在的用户ID:"+params.get("id");
        }
        return JsonUtil.jsonConfig(user,null);
    }

    //根据id清缓存
    @CacheEvict(value = {"User"},key = "'User_id_' + #id")
    @RequestMapping(value = "del/{id}", method = RequestMethod.GET)
    public Object flushRedis(@PathVariable long id){

        return "redis缓存User_id_"+id+"清除！";
    }

    //清除User所有缓存
    @CacheEvict(value = {"User"}, allEntries = true)
    @RequestMapping(value = "flushall", method = RequestMethod.GET)
    public Object flushAllByUser(){

        return "redis缓存User清空！";
    }

    //更新User缓存
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public Object updateUser(@PathVariable long id, String name){
        int num = userService.updateUser(id, name);

        return "受影响数据"+num+"条！";
    }

    @RequestMapping(value = "/insert/{id}/{name}/{code}", method = RequestMethod.GET)
    public Object insert(@PathVariable int id, @PathVariable String name, @PathVariable String code){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setCode(code);

        userService.insert(user);
        return "新增用户成功：" + JSONArray.fromObject(user);
    }


    //事务回滚
    @RequestMapping(value = "/transaction/insert", method = RequestMethod.GET)
    public Object insertList(){
        List<User> users = new ArrayList<>();

        //1
        User user = new User();
        user.setId(1);
        user.setName("李四");
        user.setCode("si-9527");
        users.add(user);
        //2
        User userOther = new User();
        userOther.setId(2);
        userOther.setName("张三");
        userOther.setCode("shan-8888");
        users.add(userOther);

        userService.insertList(users);
        return "测试事务结束！";
    }

}
