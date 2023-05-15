package com.xjy.mybatisdemo.plus;


import javax.sql.DataSource;

import com.xjy.mybatisdemo.plus.entity.User;
import com.xjy.mybatisdemo.plus.mapper.PlusUserMapper;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;


public class NoSpring {

    private static final SqlSessionFactory sqlSessionFactory = initSqlSessionFactory();

    public static void main(String[] args) {

        //初始化
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            //创建mapper对象
            PlusUserMapper mapper = session.getMapper(PlusUserMapper.class);
            mapper.selectById(1);
        }
    }

    //工厂方法
    public static SqlSessionFactory initSqlSessionFactory() {
        DataSource dataSource = dataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("Production", transactionFactory, dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration(environment);
        //在这里添加Mapper
        configuration.addMapper(PlusUserMapper.class);
        configuration.setLogImpl(StdOutImpl.class);
        return new MybatisSqlSessionFactoryBuilder().build(configuration);
    }

    //连接进行
    public static DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }





}