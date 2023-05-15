package com.xjy.mybatisdemo.mybatis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Provider implements Serializable {

    private Integer id;
    private String name;

    List<Consumer> consumers;

}
