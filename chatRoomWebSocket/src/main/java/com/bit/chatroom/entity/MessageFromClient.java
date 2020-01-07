package com.bit.chatroom.entity;

//前端发来的
// 群聊{"msg":"777","type":1}
//私聊{"to":"0-","msg":"3333","type":2}
//需要把字符串还原成对象，再通过get、set方法操作

import lombok.Data;

@Data
public class MessageFromClient {
//聊天信息
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    //聊天类别，1表示群聊，2表示私聊
    private String type;

    //私聊的对象SessionID
private String to;


}

