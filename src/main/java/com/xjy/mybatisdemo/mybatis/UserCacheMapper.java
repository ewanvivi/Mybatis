package com.xjy.mybatisdemo.mybatis;

import com.xjy.mybatisdemo.mybatis.pojo.User;

public interface UserCacheMapper {

    User selectOne(int id, String name);
}
