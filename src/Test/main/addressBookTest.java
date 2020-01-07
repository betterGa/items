package main;

import com.oracle.xmlns.internal.webservices.jaxws_databinding.SoapBindingParameterStyle;
import org.junit.Test;

import java.sql.*;
import java.util.Scanner;

//测试首页面，可是单元测试里面好像不能在console中输入
public class addressBookTest {


addressBook addresbook=new addressBook();
    @Test
    public void testHomePageToChoose(){
addresbook.homePageTochoose();
    }

    @Test
    //这个写法是可以的，但是因为输入的是String类型，就算是split了也还是String类型，
    //写sql语句就会遇到问题，应当用占位符并结合setSting的方式。
    public void insertInto1() throws SQLException, ClassNotFoundException {
       // addresbook.insertInto("1,2,3,4");

        //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //获取连接
        Connection connection=DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/first?useSSL=false","root","jdpy1229jiajia");
        connection.setAutoCommit(false);
        String sql = "insert into mine7 values ('1','2','3','4')";
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
            int resultRows=statement.executeUpdate(sql,
                    Statement.RETURN_GENERATED_KEYS);
            System.out.println(resultRows);
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    /*public static void main(String[] argus) throws SQLException, ClassNotFoundException { //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //获取连接
        Connection connection=DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/first?useSSL=false","root","jdpy1229jiajia");
        connection.setAutoCommit(false);

        String sql="insert into mine7 (name,address,telphone,zipcode) values(?,?,?,?)";
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setString(1,"1");
        statement.setString(2,"2");
        statement.setString(3,"3");
        statement.setString(4,"4");
        int influeRow=statement.executeUpdate();
        //额滴神啊这句太重要了，没有commit，事务就没有提交上去呢。
        connection.commit();
        System.out.println(influeRow);


    }
    */


    @Test
    public void testfun()
    {Scanner scanner=new Scanner(System.in);
        String str=scanner.nextLine();
        //我错是错在，调用nextLine,可就直接去读取下一行的数据了。
        //它不是光取了值。
        // 本来是想判断if(in.nextLine.equals("exit")。
        // 把它放在前面，后边再用nextLine读数据时，读到的已经是空格了。
        String[] date=str.split(",");
        System.out.println(date.length);
        for (int i=0;i<date.length;i++)
        {System.out.println(date[i]);}
    }




}