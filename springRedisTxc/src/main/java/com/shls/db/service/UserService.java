package com.shls.db.service;

import com.shls.db.dao.UserDao;
import com.shls.db.po.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserDao userDao;

    //根据查找用户
    public User findById(long id){
        return userDao.findById(id);
    }

    //更新用户
    public int updateUser(long id, String name){
        return userDao.updateUser(id,name);
    }


    //新增员工（报错事务回滚）
    @Transactional
    public void insertList(List<User> users){
        for(User user : users){
            userDao.insert(user);
        }
    }

    public int insert(User user){
        return userDao.insert(user);
    }
}
