package com.xjy.mybatisdemo.mybatis;

public class Mysql implements Dialect {
    @Override
    public Boolean supportPage() {
        System.out.println("我是Mysql");
        return true;
    }

    @Override
    public String getPageSql(String sql, int offset, int limit) {

        sql = sql.trim();
        boolean hasForUpdate = false;

        String forUpdatePart = "for update";

        if (sql.toLowerCase().endsWith(forUpdatePart)) {
            sql = sql.substring(0, sql.length() - forUpdatePart.length());
            hasForUpdate = true;
        }

        StringBuffer result = new StringBuffer(sql.length() + 100);
        result.append(sql).append(" limit ");

        if (offset > 0) {
            result.append(offset).append(",").append(limit);
        } else {
            result.append(limit);
        }

        if (hasForUpdate){
            result.append(forUpdatePart);
        }
        return result.toString();
    }
}
