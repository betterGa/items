import com.alibaba.druid.util.JdbcUtils;
import com.bit.chatroom.utils.JDBCUtils;
import com.bit.chatroom.utils.JDBCUtilsWizDruid;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class JDBCDemo1 {
    @Test
    public void test() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        //加载驱动

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc?useSSL=false&useUnicode=true&characterEncoding=UTF-8", "root", "jdpy1229jiajia");
//获取连接
       /* String username="zs";
        String  password="123";
        String sql="select * from jdbc.user where username='"+username+"'and password='"+password+"'";
        //select* from user where username='zs'and password='123'
*/

        /*假设从网络读取的username和password*/
        String username = "zs or 1=13";
        String password = "123123123";
        String sql = "select * from user where username=? and password=?";
        //预编译
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);

        //select * from user where username='zs' or 1=1 and password='123123123'
        //执行语句
        //Statement statement=connection.createStatement();
        //方法一：
        //statement.execute(sql); 返回布尔型，返回true就说明查询成功。
        //一般用executeQuery
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            System.out.println("登陆成功");
        } else System.out.println("登录失败");
        /* while(resultSet.next())
        {int id=resultSet.getInt("id");
            String username=resultSet.getString("username");
            String password=resultSet.getNString("password");
            System.out.println("id为"+id+",用户名"+username+",密码为"+password);}
*/

//释放资源
        connection.close();
        statement.close();
        resultSet.close();

    }


    @Test
    public void test1()

    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //加载驱动

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "jdpy1229jiajia");
        } catch (SQLException e) {
            e.printStackTrace();
        }
//获取连接

        String sql = "insert into user(username,password) values ('test','456')";
        //执行语句
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //方法一：
        //statement.execute(sql); 返回布尔型，返回true就说明查询成功。
        //一般用executeQuery
        try {
            int resultRows = statement.executeUpdate(sql,
                    Statement.RETURN_GENERATED_KEYS);
            System.out.println(resultRows);
        } catch (SQLException e) {
            e.printStackTrace();

        }


//释放资源
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test2()

    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //加载驱动

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc?useSSL=false", "root", "jdpy1229jiajia");
        } catch (SQLException e) {
            e.printStackTrace();
        }
//获取连接

        String sql = "delete from user where id=5";
        //执行语句
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //方法一：
        //statement.execute(sql); 返回布尔型，返回true就说明查询成功。
        //一般用executeQuery
        try {
            int resultRows = statement.executeUpdate(sql,
                    Statement.RETURN_GENERATED_KEYS);
            System.out.println(resultRows);
        } catch (SQLException e) {
            e.printStackTrace();

        }


//释放资源
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test3() throws SQLException {

        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;

        try{
connection=JDBCUtils.getConnection();
//当使用JDBCUtils这个类时,就会调用它的静态代码块，就先进行了加载驱动
String sql="select * from user";
statement=connection.createStatement();
resultSet=statement.executeQuery(sql);
while (resultSet.next())
{int id=resultSet.getInt("id");
String username=resultSet.getString("username");
String password=resultSet.getString("password");
System.out.println("id为"+id+"，用户名为"+username+",密码为"+password);}
}
        catch(SQLException e){}
            finally
        { JDBCUtils.closeResources(connection,statement,resultSet);




        }
    }



@Rollback(false)
    @Test
    public void test4() throws SQLException {
        Connection connection=null;
        PreparedStatement statement=null;
      ResultSet resultSet=null;

        try{
            connection=JDBCUtils.getConnection();
            String sql="select * from user where id = ? and username = ?";
            statement=connection.prepareStatement(sql);

            statement.setInt(1,1);
            statement.setString(2,"ZS");
            //替代第几个占位符，从1开始

            resultSet=statement.executeQuery();
            while (resultSet.next())
            {int id=resultSet.getInt("id");
                String username=resultSet.getString("username");
                String password=resultSet.getString("password");
                System.out.println("id为"+id+"，用户名为"+username+",密码为"+password);}

        }
        catch (SQLException e){}
        finally {
            JDBCUtils.closeResources(connection, statement, resultSet);

        }
    }


    @Test
@Rollback(false)
public void testInsert()
{Connection connection=null;
PreparedStatement statement=null;
    try{
connection=JDBCUtils.getConnection();
String sql="insert into user (username,password) values(?,?)";
statement=connection.prepareStatement(sql);
    statement.setString(1,"java1");
    statement.setString(2,DigestUtils.md5Hex("java1"));
int influeRow=statement.executeUpdate();

        Assert.assertEquals(1,influeRow);
    }
    catch (SQLException e){}
    finally {
        JDBCUtils.closeResources(connection,statement);
    }}


    @Test
    @Rollback(false)
    public void testInsertwizGruid()
    {Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection=JDBCUtilsWizDruid.getConnection();
            String sql="insert into user (username,password) values(?,?)";
            statement=connection.prepareStatement(sql);
            statement.setString(1,"java2");
            statement.setString(2,DigestUtils.md5Hex("java2"));
            int influeRow=statement.executeUpdate();

            Assert.assertEquals(1,influeRow);
        }
        catch (SQLException e){}
        finally {
            JDBCUtilsWizDruid.closeResources(connection,statement);
        }}


    @Test
    public void testquerywizGruid() throws SQLException {

        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;

        try{
            connection=JDBCUtilsWizDruid.getConnection();
//当使用JDBCUtils这个类时,就会调用它的静态代码块，就先进行了加载驱动
            String sql="select * from user";
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            while (resultSet.next())
            {int id=resultSet.getInt("id");
                String username=resultSet.getString("username");
                String password=resultSet.getString("password");
                System.out.println("id为"+id+"，用户名为"+username+",密码为"+password);}
        }
        catch(SQLException e){}
        finally
        { JDBCUtilsWizDruid.closeResources(connection,statement,resultSet);
        }
    }
}













