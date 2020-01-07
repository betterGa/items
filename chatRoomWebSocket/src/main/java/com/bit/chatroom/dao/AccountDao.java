package com.bit.chatroom.dao;
//用户模块的dao，要实现用户的注册和登录

import com.bit.chatroom.entity.user;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;

public class AccountDao extends Basedao {
    //用户登录--查询query(select)

    public user userLogin(String username,String passsword) throws SQLException
    {Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        user user2=null;
        try{
            connection=getConnection();
            String sql="select * from user where username=? and password=?";
            statement=connection.prepareStatement(sql);
            statement.setString(1,username);
            statement.setString(2,DigestUtils.md5Hex(passsword));
        resultSet=statement.executeQuery();
        if(resultSet.next())
        {user2=getUserInfo(resultSet);}

        } catch (SQLException e) {
           System.err.println("查询用户信息出错");
        }
        finally {
            closeResources(connection,statement,resultSet);
        }
        return user2;
    }


    //需要把resultSet数据表信息转换成entity实体中的User类。
        public user getUserInfo(ResultSet resultSet) throws SQLException
        {user user1=new user();
        user1.setId(resultSet.getInt("id"));
        user1.setUsername(resultSet.getString("username"));
        user1.setPassword(resultSet.getString("password"));
        return user1;}



    //用户注册--插入insert
public boolean userRegister(user user3)
{boolean issuccess=false;//不报错的话再改为true
    String userName=user3.getUsername();
String password=user3.getPassword();
Connection connection=null;
PreparedStatement statement=null;
try {
    connection = getConnection();
    String sql = "insert into user(username,password) values (?,?)";
    statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//主键受影响行数
    statement.setString(1, userName);
    statement.setString(2, DigestUtils.md5Hex(password));
    issuccess = (statement.executeUpdate() == 1);
}
catch (Exception ex)
{System.err.println("用户注册失败");ex.printStackTrace();}
     finally
    {closeResources(connection,statement);}


return issuccess;
}


}
