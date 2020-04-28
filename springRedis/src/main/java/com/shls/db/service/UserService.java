package com.shls.db.service;

import com.shls.db.dao.UserDao;
import com.shls.db.po.User;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserDao userDao;

    //根据查找用户
    public User findById(long id){
        return userDao.findById(id);
    }

    //根据查找用户
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<User> findByIds(List<Long> idsList){
        return userDao.findByIds(idsList);
    }


    //根据查找用户
    public User findByIdAndName(long id, String code){
        return userDao.findByIdAndName(id,code);
    }

    //根据查找用户
    public User findByIdAndNameByMap(HashMap<String, Object> params){
        return userDao.findByIdAndNameByMap(params);
    }

    //更新用户
    public int updateUser(long id, String name){
        return userDao.updateUser(id,name);
    }

    //测试同类方法事务的传播性(不生效)
    public void insertList(List<User> users){
        insertList2(users);
    }

    //新增员工（报错事务回滚）
    @Transactional
    public void insertList2(List<User> users){
        for(User user : users){
            userDao.insert(user);
        }
        throw new RuntimeException("999996666666");
    }

    public int insert(User user){
        return userDao.insert(user);
    }
}