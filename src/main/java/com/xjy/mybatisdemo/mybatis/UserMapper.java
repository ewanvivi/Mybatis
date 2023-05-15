package com.xjy.mybatisdemo.mybatis;

import com.xjy.mybatisdemo.mybatis.pojo.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@CacheNamespace
public interface UserMapper {
    //    User selectOne(@Param("id") int id, @Param("name") String name);
//    User selectOne(int id, String name);

    User selectOne(String name,int id);


    //    int insert(@Param("name") String name, @Param("pwd") String pwd);
//
    List<User> list();
//
//    List<User> listByPage(@Param("pageNum")int pageNum, @Param("pageSize")int pageSize);

    List<User> selectAll();

    User selectOneByTableName(String tableName, Integer integer);

//    List<Temp> selectTemp();

//    List<User> selectByConstructor();

//    Cursor<User> selectByCursor();

    void insertUser(User user);

    void selectAAA(RowBounds rowBounds, ResultHandler resultHandler);

    String selectBBB(RowBounds rowBounds, ResultHandler resultHandler);

}
