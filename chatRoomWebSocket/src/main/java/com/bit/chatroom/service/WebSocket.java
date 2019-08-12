package com.bit.chatroom.service;

import javax.servlet.annotation.WebServlet;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
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




