package com.bit.chatroom.controller;

import com.bit.chatroom.dao.AccountDao;
import com.bit.chatroom.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/doRegister")
public class AccountController extends HttpServlet {
private AccountService accountService=new AccountService();

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
String username=request.getParameter("username");
String password=request.getParameter("password");
response.setContentType("text/html;charset=utf8");
    PrintWriter writer=response.getWriter();
if(accountService.userRegister(username,password))
    //用户注册成功，弹框提示,返回登录界面
{
    writer.println("<script>alert(\"注册成功！\");\n" +
            "window.location.href=\"/index.html\";\n" +
            "\n" +
            "</script>");
    //参照popup.html
}



    //用户登录失败，可能是用户名已存在，或是数据库存在问题，返回原页面
    else{
    writer.println("<script>alert(\"注册失败！\");\n" +
            "window.location.href=\"/registration.html\";\n" +
            "\n" +
            "</script>");
}


}

@Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
{doGet(request,response);}




}
