package com.shls.db.dao;

import com.shls.db.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface UserDao {
    //@CachePut 是先执行方法，然后把 [返回值] 保存或更新到缓存中
    //@Cacheable 会先查询缓存，如果缓存中存在，则不执行方法
    //@CacheEvict 用来标注在需要清除缓存元素的方法或类上的

    //查找员工(根据id加入缓存)
    @Cacheable(value = {"User"}, key = "'User_id_' + #p0")
    User findById(@Param("id") long id);

    @Cacheable(value = {"User"}, keyGenerator = "defaultKeyGenerator")
    List<User> findByIds(@Param("ids") List<Long> ids);

    @Cacheable(value = {"User"}, key = "'findByIdAndNameByMap_' + #p0")
    User findByIdAndNameByMap(@Param("params") HashMap<String, Object> params);

    //查找员工(根据id加入缓存)
    @Cacheable(value = {"User"}, key = "'User_id_' + #p0 + '_' + #p1 ")
    User findByIdAndName(@Param("id") long id, @Param("code") String code);

    //更新员工(更新缓存,只能删除缓存,update返回值是int)
    @CacheEvict(value = {"User"}, key = "'User_id_' + #p0")
    int updateUser(@Param("id") long id, @Param("name") String name);

    //添加员工(删除User所有缓存)
    @CacheEvict(value = {"User"}, allEntries = true)
    int insert(User user);
}
