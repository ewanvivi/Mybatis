package com.xjy.mybatisdemo.plus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.xjy.mybatisdemo.mybatis.pojo.Consumer;
import com.xjy.mybatisdemo.mybatis.pojo.Provider;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class User implements Serializable{
    private Integer id;

    private String name;

    @TableField(exist = false)
    private String haha;

    @TableField(exist = false)
    private String test;

//    private Temp temp;

    @TableField(exist = false)
    private Provider provider;

    @TableField(exist = false)
    List<Consumer> consumers;
    public User() {
    }



    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
