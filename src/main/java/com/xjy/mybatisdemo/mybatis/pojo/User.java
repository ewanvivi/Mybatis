package com.xjy.mybatisdemo.mybatis.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class User implements Serializable{
    private Integer id;

    private String name;

    private String haha;

    private String test;

//    private Temp temp;

    private Provider provider;


    List<Consumer> consumers;
    public User() {
    }



    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
