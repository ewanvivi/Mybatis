package com.xjy.mybatisdemo.mybatis;

public interface Dialect {

    Boolean supportPage();

    String getPageSql(String sql, int offset, int limit);


}
