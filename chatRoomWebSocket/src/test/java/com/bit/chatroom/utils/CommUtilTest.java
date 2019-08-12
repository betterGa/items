package com.bit.chatroom.utils;

import com.bit.chatroom.entity.user;
import com.bit.chatroom.utils.CommUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

public class CommUtilTest {



@Test
public void gsaonTest1()
{user user1=new user();
user1.setId(12);
user1.setUsername("xiaoJia");
user1.setPassword("eat");
String Jsonstr=CommUtil.objectToJson(user1);
System.out.println(Jsonstr);
}


@Test
public void gsonTest2()
{String jsonstr="{\"id\":12,\"username\":\"xiaoJia\",\"password\":\"eat\"}";
user user2= (user) CommUtil.jsonToObject(jsonstr,user.class);
System.out.println(user2);

}






    @Test
    public void loadProperties() {
    String fileneame="datasource.properties";
        Properties properties=CommUtil.loadProperties(fileneame);
        String username=properties.getProperty("username");
        //如果加载资源配置文件成功，url的值一定不为空，在此使用断言
        Assert.assertNotNull(username);
    }
}

