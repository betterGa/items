package com.bit.chatroom.utils;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Properties;
import java.util.Stack;

public class JDBCUtils {
    private static String drtvename;
    private static String url;
    private static String username;
    private static String password;

    //不论进行数据库说明操作，首先都要加载这些属性。
    // 而需要再类加载时就能加载这四个属性，而且只加载一次就行，
    //使用静态代码块。

    static
    {Properties properties=CommUtil.loadProperties("db.properties");
    //之前单元测试测过，可放心使用
        drtvename=properties.getProperty("drivername");
        url=properties.getProperty("url");
        username=properties.getProperty("username");
        password=properties.getProperty("password");

    //接下来，加载驱动，加载一次即可：
        try {
            Class.forName(drtvename);
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
        System.err.println("加载数据库驱动出错");

        }}

        //但是获取连接不能放到静态代码块中，因为不同用户使用的连接是不同的。

public static Connection getConnection()

{
    try {
        return DriverManager.getConnection(url,username,password);
    } catch (SQLException e) {
       // e.printStackTrace();
    System.err.println("获取数据库连接出错");
    }
    return null;
}
//关闭数据库资源操作

//方法的重载：一个类中方法名称相同，参数列表不通
    //有时不需要关闭三个资源，用到方法的重载
public static void closeResources(Connection connection,Statement statement)
{
    if(connection!=null) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    if (statement != null) {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet)
    {
        closeResources(connection,statement);
        //在这里调用上面的两个参数的。
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









