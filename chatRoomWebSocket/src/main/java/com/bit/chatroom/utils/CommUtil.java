package com.bit.chatroom.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sun.plugin2.ipc.InProcEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//用于封装共有的工具方法，如加载配置工具，所有的工具类都属于静态方法
public class CommUtil {

    private static final Gson gson=new GsonBuilder().create();
//和datasource=DruidDataSourceFactory.createDataSource(properties)很像，都是"create"

    private CommUtil(){}

public static Properties loadProperties(String fileName)
{Properties properties=new Properties();
    InputStream in=CommUtil.class.getClassLoader().getResourceAsStream(fileName);
    //获取类加载器下的和它同目录的所有文件

    try {
        properties.load(in);
    } catch (IOException e) {
        e.printStackTrace();
    }

    return properties;
}



    public static String objectToJson(Object obj)
    {return gson.toJson(obj);}


    public static Object jsonToObject(String jsonstr,Class objclass)
    {return gson.fromJson(jsonstr,objclass);}


}
