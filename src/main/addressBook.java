package main;

import java.sql.*;
import java.util.Scanner;

public class addressBook {

    //首先需要用户进行登录验证，
    // 这里我用我自己的MySQL用户名与密码进行登录。
    public void homePageTochoose()
    {
        System.out.println("欢迎使用通讯录系统，按1进入");
        //用户选择"1"后，就加载驱动、连接数据库、执行新建数据库的操作，
        Scanner in=new Scanner(System.in);
            if(in.nextInt()==1) {
                try {
                    JDBCFuction();
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }



    }

    //用户存储联系人信息。
    public void storeAddr() throws SQLException, ClassNotFoundException {
        System.out.println("需要存储联系人信息,请按照以下格式输入：");
        System.out.println("姓名，地址，电话，邮编");
        Scanner in=new Scanner(System.in);
        while (in.hasNext())
        {String date=in.nextLine();
            if(date.equals("exit")) break;

            //获得date后，需要将它作为元组插入到表中
          insert(date);
            System.out.println("已将该信息存入通讯录，如要退出存储请输入‘exit’,否则继续输入联系人信息");
        }
//存储完毕以后，用户可选择除了存储以外的功能
      chooseOtherF();
    }

    //将加载驱动、连接数据库、执行新建数据库的操作，封装在本类中。
    public void JDBCFuction()
            throws ClassNotFoundException,SQLException
    {
        //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //获取连接
        Connection connection=DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/first?useSSL=false","root","jdpy1229jiajia");
        connection.setAutoCommit(false);
        //每次都会执行语句，为用户建表mine
        String sql=" create table mine(name char(20),address char(20), telphone char(11),zipcode char(6))default charset=utf8";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        //用commit结束事务。
        connection.commit();
        //释放资源
    connection.close();
    preparedStatement.close();
    }

       //将信息按","拆分，然后作为元组插入表中
public void insert(String date) throws ClassNotFoundException, SQLException {
    String[] information=date.split(",");
    //拼成一个sql语句
    String sql="insert into mine( name,address,telphone,zipcode) values(?,?,?,?)";
    //加载驱动
    Class.forName("com.mysql.jdbc.Driver");
    //获取连接
    Connection connection=DriverManager.getConnection
            ("jdbc:mysql://localhost:3306/first?useSSL=false","root","jdpy1229jiajia");
    connection.setAutoCommit(false);
    PreparedStatement preparedStatement=connection.prepareStatement(sql);
   for(int i=0;i<4;i++)
    { preparedStatement.setString(i+1,information[i]);
    }
    int res=preparedStatement.executeUpdate();//执行sql语句
    if(res>0){
        System.out.println("数据录入成功");

}
//事务一定要提交啊！！！
// 否则只是在本地程序OK fine，在数据库中并没有添加元组。
    connection.commit();
    //关闭资源
    connection.close();
    preparedStatement.close();
}



/*
//试试不使用占位符，
那就得注意单引号问题，数字类型可以不带单引号，其他必须带。
    public void insert(String date) throws ClassNotFoundException, SQLException {
        String sql="insert into mine7( name,address,telphone,zipcode) values("+date+");";
        //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //获取连接
        Connection connection=DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/first?useSSL=false","root","jdpy1229jiajia");
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        int res=preparedStatement.executeUpdate();//执行sql语句
        if(res>0){
            System.out.println("数据录入成功");

        }
        connection.commit();
        //关闭资源
        connection.close();
        preparedStatement.close();
    }
*/

public void chooseOtherF() throws SQLException, ClassNotFoundException {
    System.out.println("请选择其他功能:1.显示 2.查询 3.修改 4.排序 8.继续存储");
Scanner in=new Scanner(System.in);
while (in.hasNext())
{
    String instruction=in.nextLine();
    if(instruction.equals("exit")) break;
    if(instruction.equals("1")) displayAll();
    if(instruction.equals("2")) SearchForName();
    if(instruction.equals("3")) alter();
    if(instruction.equals("4")) sort();
    if(instruction.equals("8")) storeAddr();
}
}

//排序不能用预编译，因为占位符带进去会是'name'，而不是name，
    //起不到排序的作用
public void sort()throws SQLException,ClassNotFoundException
{
    System.out.println("请输入排序依据：1.姓名 2.邮编 3.升序 4.降序；输入格式：1,3");
    Scanner in=new Scanner(System.in);
    String sql=null;
            ;
    while (in.hasNext())
    {String choose=in.nextLine();
    String chose[]=choose.split(",");;
    if(chose[0].equals("1")&&chose[1].equals("3"))
        sql="select * from mine order by NAME ASC";
        if(chose[0].equals("1")&&chose[1].equals("4"))
            sql="select * from mine order by NAME DESC";
        if(chose[0].equals("2")&&chose[1].equals("3"))
            sql="select * from mine order by ZIPCODE ASC";
        if(chose[0].equals("2")&&chose[1].equals("4"))
            sql="select * from mine order by ZIPNODE DESC";

        //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //获取连接
        Connection connection=DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/first?useSSL=false","root","jdpy1229jiajia");
        connection.setAutoCommit(false);
        //执行SQL语句,默认是降序

        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery(sql);
        while (resultSet.next())
        {String name=resultSet.getString(
                "name");
            String address=resultSet.getString("address");
            String telephone=resultSet.getString("telphone");
            String zipcode=resultSet.getString("zipcode");
            System.out.println("姓名为"+name+",地址为"+address+"，电话为"+
                    telephone+"，邮编为"+zipcode);}
        //释放资源
        connection.close();
       statement.close();
        resultSet.close();
        chooseOtherF();}}


public void alter() throws ClassNotFoundException, SQLException {
    System.out.println("请输入要修改的联系人所有信息，" +
            "请按照以下格式输入："+
            "姓名，地址，电话，邮编");
    Scanner in=new Scanner(System.in);
    while (in.hasNext())
    {String Amessage=in.nextLine();
        //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //获取连接
        Connection connection=DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/first?useSSL=false","root","jdpy1229jiajia");
        connection.setAutoCommit(false);
        //执行SQL语句
        String sql=" update mine set address=?,telphone=?,zipcode=? where name=?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        String[] Asplit=Amessage.split(",");
        preparedStatement.setString(4,Asplit[0]);
        preparedStatement.setString(1,Asplit[1]);
        preparedStatement.setString(2,Asplit[2]);
        preparedStatement.setString(3,Asplit[3]);
        int res=preparedStatement.executeUpdate();//执行sql语句
        if(res>0){
            System.out.println("数据录入成功");

        }
//事务一定要提交啊！！！
// 否则只是在本地程序OK fine，在数据库中并没有添加元组。
        connection.commit();
        //关闭资源
        connection.close();
        preparedStatement.close();
        chooseOtherF();
}}

public void SearchForName() throws SQLException, ClassNotFoundException {
    System.out.println("请输入要查找的姓名:");
    Scanner in=new Scanner(System.in);
    while (in.hasNext())
    {String Sname=in.nextLine();
    //加载驱动
    Class.forName("com.mysql.jdbc.Driver");
    //获取连接
    Connection connection=DriverManager.getConnection
            ("jdbc:mysql://localhost:3306/first?useSSL=false","root","jdpy1229jiajia");
    connection.setAutoCommit(false);
    //执行SQL语句
    String sql="select * from mine where name = ?";
    PreparedStatement preparedStatement=connection.prepareStatement(sql);
    preparedStatement.setString(1,Sname);
    ResultSet resultSet=preparedStatement.executeQuery();
    //先跳到最后一行，如果行数为0，说明没有查到元组
        //但是这个方法有问题，它跳不回第一行了，我也不知道为什么。
      /*  resultSet.last();
        if(resultSet.getRow()==0)
    System.out.println("查无此人.");
        resultSet.first();
        */
//这个方法判断是否查到元组可行，下来学习一下，见博客第二种方法
        //https://blog.csdn.net/vvinggth204/article/details/8059222
      if(!resultSet.isBeforeFirst())  System.out.println("查无此人.");
    while (resultSet.next())
    {String name=resultSet.getString(
            "name");
        String address=resultSet.getString("address");
        String telephone=resultSet.getString("telphone");
        String zipcode=resultSet.getString("zipcode");
        System.out.println("姓名为"+name+",地址为"+address+"，电话为"+
                telephone+"，邮编为"+zipcode);}
    //释放资源
    connection.close();
    preparedStatement.close();
    resultSet.close();
    chooseOtherF();}

}


public void displayAll()
        throws ClassNotFoundException, SQLException {
    //加载驱动
    Class.forName("com.mysql.jdbc.Driver");
    //获取连接
    Connection connection=DriverManager.getConnection
            ("jdbc:mysql://localhost:3306/first?useSSL=false","root","jdpy1229jiajia");
    connection.setAutoCommit(false);
    //执行SQL语句
    String sql=" select * from mine7";
    Statement statement=connection.createStatement();
    ResultSet resultSet=statement.executeQuery(sql);
    while (resultSet.next())
    {String name=resultSet.getString(
            "name");
        String address=resultSet.getString("address");
        String telephone=resultSet.getString("telphone");
        String zipcode=resultSet.getString("zipcode");
        System.out.println("姓名为"+name+",地址为"+address+"，电话为"+
                telephone+"，邮编为"+zipcode);}
    //释放资源
    connection.close();
statement.close();
resultSet.close();
chooseOtherF();
}


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
addressBook addressbook=new addressBook();
addressbook.homePageTochoose();
addressbook.storeAddr();
}}
