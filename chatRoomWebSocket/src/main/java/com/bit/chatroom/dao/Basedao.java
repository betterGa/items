package com.bit.chatroom.dao;
//封装基础操作：数据源，获取连接，关闭资源
//所有业务都需要继承这个dao层

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.bit.chatroom.utils.CommUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Stack;

public class Basedao {
    private static DataSource dataSource;
    static
    {Properties properties=CommUtil.loadProperties("datasource.properties");
        try {
            dataSource=DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            System.err.println("数据源加载失败");
        }
    }

//获取数据库连接,只让子类使用
    protected Connection getConnection()
    {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
           System.err.println("获取连接失败");
        }
    return null;
    }


    //关闭资源 Connection,Statement,(ResultSet)
    protected void closeResources(Connection connection, Statement statement)
    {if(connection!=null) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    if(statement!=null) {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }


    protected void closeResources(Connection connection, Statement statement, ResultSet resultSet) throws SQLException
    {closeResources(connection,statement);
    if (resultSet!=null)
    resultSet.close();}}
