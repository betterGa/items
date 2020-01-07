package main;

import java.sql.*;
import java.util.Scanner;
public class ProductSales {

    //刚进入子系统需要选择产品or客户模块
    public void homePageTochoose() throws SQLException, ClassNotFoundException {
        System.out.println("选择功能 1:产品信息管理 2.客户信息管理 3.下订单 4.查询订单 5.查询库存 6.统计");
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String choose = in.nextLine();

            if (choose.equals("1")) turnToProduct();
            if (choose.equals("2")) turnToClient();
            if (choose.equals("3")) Ordering();
        if (choose.equals("4"))  SearchForO();
        if(choose.equals("5")) SearchForN();
        if(choose.equals("6")) {
            statisticsAmount();
        }

        }
    }

    //产品信息管理
    public void turnToProduct() throws SQLException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("选择功能： 1.添加 2.修改 3.删除");
        String choose = in.nextLine();
        if (choose.equals("1")) insertPro();
        if (choose.equals("2")) alterPro();
        if (choose.equals("3")) deletePro();
        homePageTochoose();
    }

            //客户信息管理
            public void turnToClient() throws SQLException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("选择功能： 1.添加 2.修改 3.删除");
        String choose = in.nextLine();
        if (choose.equals("1")) insertCli();
        if (choose.equals("2")) alterCli();
        if (choose.equals("3")) deleteCli();
        homePageTochoose();
    }

    //产品模块，添加元组功能
    public void insertPro() throws SQLException, ClassNotFoundException {

        //首先需要建表
        try {
            createProduct();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //接下来插入元组
        System.out.println("添加产品信息,请按照以下格式输入：");
        System.out.println("产品号,产品名,单价,数量");
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String date = in.nextLine();
            if (date.equals("exit")) break;
            //获得date后，需要将它作为元组插入到表中
            try {
                insertIntoP(date);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("已将该产品信息存入，如要退出存储请输入‘exit’,否则继续添加产品信息");
        }
//存储完毕以后，用户可选择除了存储以外的功能
        homePageTochoose();
    }

    public void insertIntoP(String date) throws ClassNotFoundException, SQLException {
        String[] information = date.split(",");
        //拼成一个sql语句
        String sql = "insert into product(ID,name,unitPiece,amount) values(?,?,?,?)";
        //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //获取连接
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/second?useSSL=false", "root", "jdpy1229jiajia");
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, Integer.parseInt(information[0]));
        preparedStatement.setString(2, information[1]);
        preparedStatement.setDouble(3, Double.parseDouble(information[2]));
        preparedStatement.setInt(4, Integer.parseInt(information[3]));
        int res = preparedStatement.executeUpdate();//执行sql语句
        if (res > 0) {
            System.out.println("数据录入成功");
        }
        connection.commit();
        //关闭资源
        connection.close();
        preparedStatement.close();
    }

    //建表product
    public void createProduct() throws SQLException, ClassNotFoundException {// 通过用户名与密码连接数据库
        //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
//获取连接,题目一用的是first数据库，本题用的是second数据库。
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/second?useSSL=false", "root", "jdpy1229jiajia");
        connection.setAutoCommit(false);
//建表product
        //属性：产品号、产品名、单价、数量。
        String sql = " create table  if not exists product (ID int,name char(20), unitPiece double ,amount int)default charset=utf8";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        //用commit结束事务。
        connection.commit();
        //释放资源
        connection.close();
        preparedStatement.close();
    }

    //修改产品信息
    public void alterPro() throws SQLException, ClassNotFoundException {
        System.out.println("根据产品号修改产品的其他信息，" +
                "请按照以下格式输入：" +
                "产品号,产品名,单价,数量");
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String Amessage = in.nextLine();
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接
            Connection connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/second?useSSL=false", "root", "jdpy1229jiajia");
            connection.setAutoCommit(false);
            //执行SQL语句
            String sql = " update product set name=?,unitPiece=?,amount=? where ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            String[] Asplit = Amessage.split(",");
            preparedStatement.setInt(4, Integer.parseInt(Asplit[0]));
            preparedStatement.setString(1, Asplit[1]);
            preparedStatement.setDouble(2, Double.parseDouble(Asplit[2]));
            preparedStatement.setInt(3, Integer.parseInt(Asplit[3]));
            int res = preparedStatement.executeUpdate();//执行sql语句
            if (res > 0) {
                System.out.println("数据录入成功");
            }
//事务一定要提交啊！！！
// 否则只是在本地程序OK fine，在数据库中并没有添加元组。
            connection.commit();
            //关闭资源
            connection.close();
            preparedStatement.close();
            homePageTochoose();
        }
    }

    //删除产品信息
    public void deletePro() throws SQLException, ClassNotFoundException {
        //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //获取连接
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/second?useSSL=false", "root", "jdpy1229jiajia");
        connection.setAutoCommit(false);
        //执行SQL语句
        String sql = " delete from product where ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println("请输入删除产品的ID：");
        Scanner in = new Scanner(System.in);
        preparedStatement.setInt(1, Integer.parseInt(in.nextLine()));
        int res = preparedStatement.executeUpdate();
        //提交事务
        connection.commit();
        if (res > 0) System.out.println("删除成功");
        //关闭资源
        connection.close();
        preparedStatement.close();
    }

    //选择了客户模块的添加功能
    public void insertCli() throws SQLException, ClassNotFoundException {  //首先需要建表
        try {
            createClient();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //接下来插入元组
        System.out.println("添加客户信息,请按照以下格式输入：");
        System.out.println("顾客号,顾客名,地,电话,信贷状况,预付款");
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String date = in.nextLine();
            if (date.equals("exit")) break;
            //获得date后，需要将它作为元组插入到表中
            try {
                insertIntoC(date);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("已将该客户信息存入，如要退出存储请输入‘exit’,否则继续添加客户信息");
        }
//存储完毕以后，用户可选择除了存储以外的功能
        homePageTochoose();
    }

    //建表client
    public void createClient() throws SQLException, ClassNotFoundException {// 通过用户名与密码连接数据库
        //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
//获取连接,题目一用的是first数据库，本题用的是second数据库。
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/second?useSSL=false", "root", "jdpy1229jiajia");
        connection.setAutoCommit(false);
//建表product
        //属性：顾客号,顾客名,地址,电话,信贷状况,预付款
        String sql = " create table if not exists client(ID int,name char(20),address char(20) ,telephone char(11),credit char(10),prepay char(20))default charset=utf8";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        //用commit结束事务。
        connection.commit();
        //释放资源
        connection.close();
        preparedStatement.close();
    }

    //将元组插入clinet表中
    public void insertIntoC(String date) throws ClassNotFoundException, SQLException {
        String[] information = date.split(",");
        //拼成一个sql语句
        String sql = "insert into client(ID,name,address,telephone,credit,prepay) values(?,?,?,?,?,?)";
        //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //获取连接
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/second?useSSL=false", "root", "jdpy1229jiajia");
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, Integer.parseInt(information[0]));
        for (int i = 2; i < 7; i++) {
            preparedStatement.setString(i, information[i - 1]);
        }
        int res = preparedStatement.executeUpdate();//执行sql语句
        if (res > 0) {
            System.out.println("数据录入成功");
        }
        connection.commit();
        connection.close();
        preparedStatement.close();
    }

    //修改客户信息
    public void alterCli() throws SQLException, ClassNotFoundException {
        System.out.println("根据顾客号修改产品的其他信息，" +
                "请按照以下格式输入：" +
                "顾客号,顾客名,地,电话,信贷状况,预付款");
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String Amessage = in.nextLine();
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接
            Connection connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/second?useSSL=false", "root", "jdpy1229jiajia");
            connection.setAutoCommit(false);
            //执行SQL语句
            //ID,name,address,telephone,credit,prepay

            String sql = " update client set name=?,address=?,telephone=?,credit=?,prepay=? where ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            String[] Asplit = Amessage.split(",");
            preparedStatement.setInt(6, Integer.parseInt(Asplit[0]));
            for (int i = 1; i < 6; i++) {
                preparedStatement.setString(i, Asplit[i]);
            }
            int res = preparedStatement.executeUpdate();//执行sql语句
            if (res > 0) {
                System.out.println("数据录入成功");
            }
            connection.commit();
            connection.close();
            preparedStatement.close();
            homePageTochoose();
        }
    }

    //删除客户信息
    public void deleteCli() throws SQLException, ClassNotFoundException { //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //获取连接
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/second?useSSL=false", "root", "jdpy1229jiajia");
        connection.setAutoCommit(false);
        //执行SQL语句
        String sql = " delete from client where ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println("请输入删除顾客的ID：");
        Scanner in = new Scanner(System.in);
        preparedStatement.setInt(1, Integer.parseInt(in.nextLine()));
        int res = preparedStatement.executeUpdate();
        //提交事务
        connection.commit();
        if (res > 0) System.out.println("删除成功");
        //关闭资源
        connection.close();
        preparedStatement.close();
    }

    //下订单
    public void Ordering() throws SQLException, ClassNotFoundException {
        //首先需要建theOrder表
        createOrder();
        System.out.println("即将生成订单，请输入订单信息（订单号将自动生成）");
        System.out.println("输入格式为:顾客ID,订购项数,订购日期(XXXX-XX-XX),订货产品号,数量");
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String theOrder = in.nextLine();
            if (theOrder.equals("exit")) homePageTochoose();
            //获得theOrder后，需要将它作为元组插入到表中
            try {
                insertIntoO(theOrder);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("已生成订单，如要退出下单请输入‘exit’,否则继续生成新订单");
        }


    }

    //建theOrder订单表
    public void createOrder() throws SQLException, ClassNotFoundException {
        // 通过用户名与密码连接数据库
        //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
//获取连接,题目一用的是first数据库，本题用的是second数据库。
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/second?useSSL=false", "root", "jdpy1229jiajia");
        connection.setAutoCommit(false);
//建表product
        //属性：订单号、顾客ID、订购项数、订购日期、订货产品号、数量。
        String sql = " create table  if not exists theOrder(ID int primary key auto_increment,clientID int,number int,orderDate date,productID int,amount int)default charset=utf8";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        //用commit结束事务。
        connection.commit();
        //释放资源
        connection.close();
        preparedStatement.close();
    }

    //将元组插入订单表
    public void insertIntoO(String theOrders) throws SQLException, ClassNotFoundException {
        String[] information = theOrders.split(",");
        //拼成一个sql语句
        String sql = "insert into theOrder(clientID,number,orderDate,productID,amount) values(?,?,?,?,?)";
        //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //获取连接
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/second?useSSL=false", "root", "jdpy1229jiajia");
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, Integer.parseInt(information[0]));
        preparedStatement.setInt(2, Integer.parseInt(information[1]));
        preparedStatement.setDate(3, Date.valueOf(information[2]));
        preparedStatement.setInt(4, Integer.parseInt(information[3]));
        preparedStatement.setInt(5, Integer.parseInt(information[4]));
        int res = preparedStatement.executeUpdate();//执行sql语句
        if (res > 0) {
            System.out.println("数据录入成功");
        }
        connection.commit();
        //关闭资源
        connection.close();
        preparedStatement.close();
    }


        //查询订单
    public void SearchForO()throws SQLException,ClassNotFoundException
    {
        System.out.println("请输入要查找的顾客ID:");
        Scanner in=new Scanner(System.in);
        while (in.hasNext())
        {String cID=in.nextLine();
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接
            Connection connection=DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/second?useSSL=false","root","jdpy1229jiajia");
            connection.setAutoCommit(false);
            //执行SQL语句
            String sql="select * from theOrder where clientID = ?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,cID);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst())  System.out.println("查无此订单.");
            //| ID | clientID | number | orderDate  | productID | amount
            while (resultSet.next())
            {int ID=resultSet.getInt("ID");
               int clientID=resultSet.getInt("clientID");
               int number=resultSet.getInt("number");
               java.util.Date orderDate =resultSet.getDate("orderDate");
               int productID=resultSet.getInt("productID");
               int amount=resultSet.getInt("amount");
                System.out.println("顾客ID为"+clientID+"订单ID为"+ID+",订购了"+number+"项产品,订购日期为"+orderDate+",产品"+
                        productID+",数量为"+amount+"个");}
            //释放资源
            connection.close();
            preparedStatement.close();
            resultSet.close();
           homePageTochoose();}}


        //查询库存

    //查询订单
    public void SearchForN()throws SQLException,ClassNotFoundException
    {
        System.out.println("请输入要查找的产品ID:");
        Scanner in=new Scanner(System.in);
        while (in.hasNext())
        {String pID=in.nextLine();
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接
            Connection connection=DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/second?useSSL=false","root","jdpy1229jiajia");
            connection.setAutoCommit(false);
            //执行SQL语句,用产品入库即建product表后的值减去订单中该产品的总数量
            String sql=" select((select product.amount as s2)-(select sum(theorder.amount) as s1 from theorder where productID=?)) as inventory from product where ID=?;";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,Integer.parseInt(pID));
            preparedStatement.setInt(2,Integer.parseInt(pID));
            ResultSet resultSet=preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst())  System.out.println("查无此订单.");
            while (resultSet.next())
            {int inventory=resultSet.getInt("inventory");

                System.out.println("库存为"+inventory);}
            //释放资源
            connection.close();
            preparedStatement.close();
            resultSet.close();
            homePageTochoose();}}

            //统计，根据顾客ID统计其订购的不同产品的数量和总金额
    //可以通过group函数求得不同产品的数量
    //但是总金额不好求，分开求解
    public void statisticsAmount()throws SQLException,ClassNotFoundException
    {
        System.out.println("请输入需要统计的顾客ID:");
        //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //获取连接
        Connection connection=DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/second?useSSL=false","root","jdpy1229jiajia");
        connection.setAutoCommit(false);
        Scanner in=new Scanner(System.in);
        String CID=in.nextLine();
        //执行SQL语句
        String sql=" select productID,sum(amount) as totalAmount from theorder where clientID=? group by productID;";
       PreparedStatement preparedStatement=connection.prepareStatement(sql);
       preparedStatement.setInt(1,Integer.parseInt(CID));
       ResultSet resultSet=preparedStatement.executeQuery();
       int[] totalAmount=new int[10];
       int[] proID=new int[10];
       int i=0;
       //这回不是直接输出了，把产品总数存在数组中，
        while (resultSet.next()) {
            proID[i]=resultSet.getInt("productID");
totalAmount[i]=resultSet.getInt("totalAmount");
i++; }
        //在执行第二个查询，把产品单价放在数组中

sql="select distinct product.ID,unitPiece from product,theorder where product.ID=theorder.productID and theorder.clientID=?";
        preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setInt(1,Integer.parseInt(CID));
        resultSet=preparedStatement.executeQuery();
        int[] unitPiece=new int[10];
        int j=0;
while (resultSet.next())
{unitPiece[j]=resultSet.getInt("unitPiece");
j++;}

        /* while (resultSet.next()) {
            int productID = resultSet.getInt("productID");
          int totalAmount = resultSet.getInt("totalAmount");
System.out.println("该顾客订购的产品，产品ID为"+productID+",总数为"+totalAmount);
        }*/
for(int k=0;k<i;k++)
{
    System.out.println("该顾客订购的产品，产品ID为"+proID[k]+",总数为"+totalAmount[k]+",总金额为"+(unitPiece[k]*totalAmount[k]));
}

        //释放资源
        connection.close();
        preparedStatement.close();
        resultSet.close();
        homePageTochoose();
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
     ProductSales productSales=new ProductSales();
     productSales.homePageTochoose();
    }
}
