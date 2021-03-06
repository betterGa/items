package com.bitstudy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Measurement {
    int iteration();//一共进行几组测试
    int countPerGroup();//每组测试指定多少次方法
 }



