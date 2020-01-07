/*package com.bit.chatroom.service;

import javax.servlet.annotation.WebServlet;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

//该注解可把当前类标记为WebSocket类
@ServerEndpoint("/websocket")
public class WebSocket {

    //需要存储所有连接到后端的websocket
    //静态：共享，不必每次打开便签就在Tomcat中新建一个实例。不必每个窗口都有ArraySet
    private static CopyOnWriteArraySet<WebSocket> clients=new CopyOnWriteArraySet<>();

    //绑定当前websocket会话
private Session session;


//建立连接时调用
@OnOpen
    public void onOpen(Session session) {
    this.session = session;
    clients.add(this);
    System.out.println("有新的连接，当前的Session ID 为：" + session.getId() + ",当前聊天室共有" + clients.size() + "人");
}


@OnError
    public void onError(Throwable e)
{System.err.println("websocket连接失败！");
e.printStackTrace();}

@OnMessage
    public void onMessage(String msg) throws IOException {//默认是群聊
    System.out.println("浏览器发来的信息是："+msg);
for(WebSocket webSocket:clients)
webSocket.sendMsg(msg);}

    public void sendMsg(String msg) throws IOException
    {this.session.getBasicRemote().sendText(msg);}



    @OnClose
    public void onClose()
    {System.out.println("有用户退出聊天室");
    clients.remove(this);
    System.out.println("当前聊天室还剩下"+clients.size()+"人");}
}
*/





//实现群聊功能
        package com.bit.chatroom.service;

import com.bit.chatroom.entity.Message2Client;
import com.bit.chatroom.entity.MessageFromClient;
import com.bit.chatroom.utils.CommUtil;

import com.sun.security.ntlm.Client;

import javax.servlet.annotation.WebServlet;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

//该注解可把当前类标记为WebSocket类
@ServerEndpoint("/websocket")
public class WebSocket {

    //需要存储所有连接到后端的websocket
    //静态：共享，不必每次打开便签就在Tomcat中新建一个实例。不必每个窗口都有ArraySet
    private static CopyOnWriteArraySet<WebSocket> clients=new CopyOnWriteArraySet<>();


    private Session session;
    /*
    群聊需要缓存所有用户列表
  */
    private static Map<String,String>names=new ConcurrentHashMap<>();



//当前客户端用户名
    private String userName;







    //建立连接时调用
    @OnOpen
    public void onOpen(Session session) throws IOException {
this.session=session;
String userName=session.getQueryString().split("=")[1];
//前端发给后端的格式是：chat.ftl第60行 webSocket = new WebSocket('ws://127.0.0.1:8080/websocket?username=' + '${username}');
//"?"表示需要传递的参数
// getQueryString()获取的内容是username=' + '${username}'
//需要的是value值，按照"="拆分即可


        this.userName=userName;

        //将客户端聊天实体保存到clients中
   clients.add(this);

   //将当前用户和SessionID保存到用户列表
        names.put(session.getId(),userName);

        System.out.println("有新的连接，SessionID为"+session.getId()+",用户名为"+userName);

//这时需要给所有在线用户一个上线通知
        Message2Client message2Client=new Message2Client();
        message2Client.setContent(userName+"上线啦");
        message2Client.setNames(names);

        //发送信息
        String jsonStr=CommUtil.objectToJson(message2Client);
        for(WebSocket webSocket:clients)
        {webSocket.sendMsg(jsonStr);}}




    @OnError
    public void onError(Throwable e)
    {
        System.err.println("WebSocket连接失败");

    }




    @OnMessage
    public void onMessage(String msg) throws IOException {

        //先将msg反序列化为MessageFronClient
        MessageFromClient messageFromClient= (MessageFromClient) CommUtil.jsonToObject(msg,MessageFromClient.class);
        if(messageFromClient.getType().equals("1"))
        {String context=messageFromClient.getMsg();

            //需要把群聊信息封装在字符串中发给所有客户端
            Message2Client message2Client=new Message2Client();
            message2Client.setContent(context);
            message2Client.setNames(names);

            //群聊发送
            for (WebSocket webSocket:clients
            {webSocket.sendMsg(CommUtil.objectToJson(message2Client));}
)
        }

else if(messageFromClient.getType().equals("2"))
        {
            //私聊信息
            String content=messageFromClient.getMsg();
            int toL=messageFromClient.getTo().length();
            String tos[]=messageFromClient.getTo().substring(0,toL-1).split("-");
List<String> lists=Arrays.asList(tos);
            //给指定的SessionID发送信息
           for(WebSocket webSocket:clients)
           {if(lists.contains(webSocket.session.getId())&&this.session.getId()!=webSocket.session.getId())
           {Message2Client message2Client=new Message2Client();
           message2Client.setContent(userName,content);
           message2Client.getNames(names);
               webSocket.sendMsg(CommUtil.objectToJson(message2Client));}


           }


        }


    }

    public void sendMsg(String msg) throws IOException
    {this.session.getBasicRemote().sendText(msg);}



    @OnClose
    public void onClose() throws IOException {//将客户端移除
            clients.remove(this);

            names.remove(session.getId());

            System.out.println("有用户下线了，用户名为" + userName);

            //这时需要给所有在线用户一个下线通知
            Message2Client message2Client = new Message2Client();
            message2Client.setContent(userName + "下线啦");
            message2Client.setNames(names);

            //发送信息
            String jsonStr = CommUtil.objectToJson(message2Client);
            for (WebSocket webSocket : clients) {
                webSocket.sendMsg(jsonStr);
            }
        }
}








