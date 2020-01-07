package com.bit.chatroom.utils;


import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

//基于DruidDateSource
public class JDBCUtilsWizDruid
{
private static DataSource dataSource;


static {
    Properties properties=CommUtil.loadProperties("datasource.properties");
    try {

        //注册驱动,连属性也获取完成了。
        dataSource=DruidDataSourceFactory.createDataSource(properties);
    } catch (Exception e) {
        //e.printStackTrace();
    System.err.println("获取数据源失败");
    }
}


public  static DruidPooledConnection getConnection()
{
    try {
        return (DruidPooledConnection) dataSource.getConnection();
    } catch (SQLException e) {
       // e.printStackTrace();
    System.err.println("连接数据库失败");
    }
return null; }

//这里用Connection类，以后传参数可以向上转型，支持更多的方法调用.


    public static void closeResources(Connection connection, Statement statement)
    {
        if(connection!=null)
        {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(statement!=null)
        {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

public static void closeResources(Connection connection, Statement statement, ResultSet  resultSet)
{closeResources(connection,statement);
    if(resultSet!=null)
    {
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}




}
