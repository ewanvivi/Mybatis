package com.xjy.mybatisdemo.mybatis;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class TestPlugin implements Interceptor {

    //获取mappedstatement   就是select、delete这种标签映射对象
    private static int MAPPEDSTATEMENT_INDEX = 0;

    //用户传入的实际参数
    private static int PARAMETEROBJECT_INDEX = 1;

    //Mappedstatement类型参数列表索引位置
    private static int ROWBOUNDS_INDEX = 2;

    //数据库语言
    private Dialect dialect;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();
        MappedStatement mappedStatements = (MappedStatement) args[MAPPEDSTATEMENT_INDEX];
        Object parameterObject = args[PARAMETEROBJECT_INDEX];
        RowBounds rowBounds = (RowBounds) args[ROWBOUNDS_INDEX];
        int offset = rowBounds.getOffset();
        int limit = rowBounds.getLimit();
        BoundSql boundSql = mappedStatements.getBoundSql(parameterObject);
        String sql = boundSql.getSql().trim();
        if (dialect.supportPage()) {
            sql = dialect.getPageSql(sql, offset, limit);
            args[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
        }
        args[MAPPEDSTATEMENT_INDEX] = createMappedStatement(mappedStatements, boundSql, sql);
        return invocation.proceed();
    }

    private MappedStatement createMappedStatement(MappedStatement mappedStatement, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = createBoundSql(mappedStatement, boundSql, sql);
        return createStatement(mappedStatement,newBoundSql);
    }

    private BoundSql createBoundSql(MappedStatement mappedStatement, BoundSql boundSql, String sql) {
        return new BoundSql(mappedStatement.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
    }

    private MappedStatement createStatement(MappedStatement mappedStatement,BoundSql boundSql) {
        Configuration configuration = mappedStatement.getConfiguration();
        String id = mappedStatement.getId();
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        StaticSqlSource staticSqlSource = new StaticSqlSource(mappedStatement.getConfiguration(), boundSql.getSql(),boundSql.getParameterMappings());
        MappedStatement.Builder builder = new MappedStatement.Builder(configuration, id, staticSqlSource, sqlCommandType);
        return builder.resultMaps(mappedStatement.getResultMaps()).build();
    }

    @Override
    public void setProperties(Properties properties) {
        String dbName = properties.getProperty("dbName");
        String prefix = "dialect.";
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<Object, Object> objectObjectEntry : properties.entrySet()) {
            String key = (String) objectObjectEntry.getKey();
            if (key != null && key.startsWith(prefix)) {
                result.put(key.substring(prefix.length()), (String) objectObjectEntry.getValue());
            }
        }

        String dialectName = result.get(dbName);

        try {
            this.dialect = (Dialect)Class.forName(dialectName).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("数据库转换异常");
        }


    }
}
