package com.xjy.mybatisdemo.mybatis;

public class Oracle implements Dialect {
    @Override
    public Boolean supportPage() {
        System.out.println("我是Oracle");
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
        result.append("select * from (");
        result.append(sql);


        return result.toString();
    }
}
