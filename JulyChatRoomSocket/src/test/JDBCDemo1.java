import org.junit.Test;

import java.sql.*;

public class JDBCDemo1 {


    @Test
    public void test() throws ClassNotFoundException, SQLException//相当于主方法
    {
        //1.加载驱动
        Class.forName("com.mysql.jdbc.Driver");
//2.获取连接
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","jdpy1229jiajia");
    //3.执行sql语句
        String sql="select * from user";

        //核心组件：Statement类执行语句
        Statement statement=connection.createStatement();
        //方法一：execute(sql)
        //statement.execute(sql);
       //方法二：executeQuery()
        ResultSet resultSet=statement.executeQuery(sql);
        //遍历结果集，认为每一行是一个对象
        while (resultSet.next())
        {int id=resultSet.getInt("id");
        String username=resultSet.getString("username");
        String password=resultSet.getNString("password");
        System.out.println("id为"+id+",用户名为："+username+",密码为："+password);}

        //4,.释放资源
        connection.close();
        statement.close();
        resultSet.close();
    }
}

/*
import org.junit.Test;

import java.sql.*;

/**
 * @Author: yuisama
 * @Date: 2019-07-27 10:53
 * @Description:

public class JDBCDemo1 {
    @Test
    // select
    public void test() {
        try {
            // 1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 2.获取连接
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/jdbc",
                            "root","satomi/317");
            // 3.执行SQL
            String sql = "select * from user";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // 遍历结果集
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                System.out.println("id为"+id+"，用户名为:"+userName+
                        "，密码为:"+password);
            }
            // 4.释放资源
            connection.close();
            statement.close();
            resultSet.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    // insert
    public void test1() {
        try {
            // 1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 2.获取连接
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/jdbc",
                            "root","satomi/317");
            // 3.执行SQL
            String sql = "insert into user(username, password) " +
                    "values ('test','456')";
            Statement statement = connection.createStatement();
            int resultRows = statement.executeUpdate(sql,
                    Statement.RETURN_GENERATED_KEYS);
            System.out.println(resultRows);
            // 4.释放资源
            connection.close();
            statement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    // delete
    public void test2() {
        try {
            // 1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 2.获取连接
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/jdbc",
                            "root","satomi/317");
            // 3.执行SQL
            String sql = "delete from user where id = 5";
            Statement statement = connection.createStatement();
            int resultRows = statement.executeUpdate(sql,
                    Statement.RETURN_GENERATED_KEYS);
            System.out.println(resultRows);
            // 4.释放资源
            connection.close();
            statement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    // select
    public void test3() {
        try {
            // 1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 2.获取连接
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/jdbc",
                            "root","satomi/317");
            // 3.执行SQL
            String userName = "zs' --";
            String password = "fdfdfdfdf";
            String sql = "select * from user where username = '"+userName+" " +
                    "and password = '"+password+"' ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // 遍历结果集
            if (resultSet.next()) {
                System.out.println("登录成功！");
            }else {
                System.out.println("登录失败！");
            }
            // 4.释放资源
            connection.close();
            statement.close();
            resultSet.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    // select
    public void test4() {
        try {
            // 1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 2.获取连接
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/jdbc",
                            "root","satomi/317");
            // 3.执行SQL
            String sql = "select * from user" +
                    " where username = ? and password = ?";
            // 预编译SQL
            PreparedStatement statement =
                    connection.prepareStatement(sql);
            String userName = "zs or 1=1";
            String password = "dfdfdfdfd";
            statement.setString(1,userName);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            // 遍历结果集
            if (resultSet.next()) {
                System.out.println("登录成功！");
            }else {
                System.out.println("登录失败！");
            }
            // 4.释放资源
            connection.close();
            statement.close();
            resultSet.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

*/