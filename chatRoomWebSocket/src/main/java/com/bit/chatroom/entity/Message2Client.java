package com.bit.chatroom.entity;

import java.util.Map;

public class Message2Client {
//聊天内容和用户列表
private String content;

//服务端登录的所有列表
private Map<String,String> names;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public void setContent(String userName,String msg)
    {this.content=userName+"说："+msg;}


    public Map<String, String> getNames(Map<String, String> names) {
        return this.names;
    }

    public void setNames(Map<String, String> names) {
        this.names = names;
    }
}
