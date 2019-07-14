package com.bitstudy;

import com.bitstudy.annotations.Benchmark;
import com.bitstudy.annotations.Measurement;
import com.bitstudy.cases.StringConcat;
import javax.swing.text.StringContent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.MemoryHandler;

public class Runner {
    public static List<BenchmarkCase> initCase()
    {List<BenchmarkCase> cases=new ArrayList<BenchmarkCase>();
        cases.add(new StringConcat());
        return cases;
    }
    private static void runMethodCase(BenchmarkCase bcase, Method method,int iteraions,int countPerGroup)
    {for(int i=0;i<iteraions;i++)
    {System.out.printf("第%d次测试%n",i+1);
        long t1=System.nanoTime();
        for(int j=0;j<countPerGroup;j++)
           {
            try {
                method.invoke(bcase);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            }long t2=System.nanoTime();
        System.out.printf("耗时:%d%n",t2-t1);
    }}

    public static void runCase(BenchmarkCase bCase)
    {Method[]methods=bCase.getClass().getMethods();int i=1;
        for(Method method:methods)
        {
            Annotation benchmark=  method.getAnnotation(Benchmark.class);
            if(benchmark==null){continue; }

            int iteration=10;
            int countPerGroup=1000;

            Measurement measurement=(Measurement) method.getAnnotation(Measurement.class);
            if(measurement!=null)
            {iteration=measurement.iteration();
                countPerGroup=measurement.countPerGroup();}
System.out.println("方法"+i+"测试性能");
            i++;
            runMethodCase(bCase,method,iteration,countPerGroup);
        System.out.println("                       ");}
    }

    public static void main(String[] args) {
        List<BenchmarkCase> bCaseList=initCase();
        for(BenchmarkCase bCase:bCaseList)
        {runCase(bCase);}
       for(int i=0;i<bCaseList.size();i++)
        System.out.println("List"+i+":"+"  "+bCaseList.get(i));
    }}
