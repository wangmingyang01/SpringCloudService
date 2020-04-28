package com.shls.db.dao;

import com.shls.db.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    User findById(long id);

    int updateUser(@Param("id") long id, @Param("name") String name);

    int insert(User user);
}
