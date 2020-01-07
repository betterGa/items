package com.bit.chatroom.controller;

import com.bit.chatroom.config.FreeMarkerListener;
import com.bit.chatroom.service.AccountService;
import com.bit.chatroom.utils.CommUtil;
import com.sun.deploy.net.HttpResponse;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.temporal.Temporal;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
    AccountService accountService=new AccountService();
    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {String userName=request.getParameter("username");
    String password=request.getParameter("password");
    response.setContentType("text/html;charset=utf8");
    PrintWriter out=response.getWriter();
if(CommUtil.strIsnull(userName)||CommUtil.strIsnull(password))
{//登录失败，返回登录页面
    out.println("<script>\n" +
            "    alert(\"用户名或密码为空\")\n" +
            "        window.location.href=\"/index.html\";\n" +
            "</script>\n" +
            "\n");
}
//用户名和密码不为空
        if(accountService.userLogin(userName,password))
        {//登录成功，跳转到聊天页面
//需要把用户名传到前端
//加载chat.ftl,在本类中写方法getTemplate
            Template template=getTemplate(request,"/chat.ftl");
            Map<String,String> map=new HashMap<>();
            map.put("username",userName);
            try {
                template.process(map,out);
            } catch (TemplateException e) {
                e.printStackTrace();
            }

        }

else {
    //用户名或密码不对，登录失败，返回登录页面，再次登录
out.println("<script>\n" +
        "    alert(\"用户名或密码不正确\")\n" +
        "        window.location.href=\"/index.html\";\n" +
        "</script>\n" +
        "\n");
}



    }




    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {doGet(request,response);}



private Template getTemplate(HttpServletRequest request,String fileName)
{Configuration cfg=(Configuration) request.getServletContext().getAttribute(FreeMarkerListener.TEMPLATE_KEY);
    try {
        return  cfg.getTemplate(fileName);
    } catch (IOException e) {
        e.printStackTrace();
    }
return  null;
}



}
