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
