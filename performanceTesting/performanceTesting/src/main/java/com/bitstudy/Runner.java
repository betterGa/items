/*Version6
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
*/



/*改为：
通过多个List元素，创建多个SteingConcat对象作为多个测试实例。需要测几组（iteration）就产生几个StringConcat对象
修改成功
    package com.bitstudy;

            import com.bitstudy.annotations.Benchmark;
            import com.bitstudy.annotations.Measurement;
            import com.bitstudy.cases.StringConcat;
            import javax.swing.text.StringContent;
            import java.io.IOException;
            import java.lang.reflect.InvocationTargetException;
            import java.lang.reflect.Method;
            import java.lang.annotation.Annotation;
            import java.util.ArrayList;
            import java.util.Iterator;
            import java.util.List;
            import java.util.ListIterator;
            import java.util.logging.MemoryHandler;

public class Runner {
    public static List<BenchmarkCase> initCase() throws IOException, IllegalAccessException, InstantiationException { List<BenchmarkCase> cases=new ArrayList<BenchmarkCase>();

    cases.add(new StringConcat());  //先加一次
        BenchmarkCase bCase=cases.get(0);//拿到bCase

        //需要拿到iteration属性
        Method[]methods=bCase.getClass().getMethods();
           Method method=methods[0];
                Annotation benchmark=  method.getAnnotation(Benchmark.class);
                if(benchmark==null) return null;

          /*int iteration=10;
            int countPerGroup=1000;//默认的default如果写了属性值可以不写

             不过需要初始化*

                int iteration=0;

                Measurement measurement=(Measurement) method.getAnnotation(Measurement.class);
                if(measurement!=null)
                {iteration=measurement.iteration(); }


        for(int j=0;j<iteration-1;j++)
        //需要几组测试用例就add-1次
        {cases.add(new StringConcat()); }
        //使用Iterator迭代输出
        Iterator<BenchmarkCase>iterator=cases.iterator();
                while (iterator.hasNext())
                {System.out.println(iterator.next());}

        return cases;
    }
    private static void runMethodCase(BenchmarkCase bcase, Method method,int countPerGroup)
    {
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
    }

    public static void runCase(BenchmarkCase bCase)
    {Method[]methods=bCase.getClass().getMethods();int i=1;
        for(Method method:methods)
        {
            Annotation benchmark=  method.getAnnotation(Benchmark.class);
            if(benchmark==null){continue; }

            //int iteration=10;
            //int countPerGroup=1000;//默认的default如果写了属性值可以不写
                                  /*不过需要初始化*
            int countPerGroup=0;
            Measurement measurement=(Measurement) method.getAnnotation(Measurement.class);
            if(measurement!=null)
            { countPerGroup=measurement.countPerGroup();}
            System.out.println("方法"+i+"测试性能");
            i++;
            runMethodCase(bCase,method,countPerGroup);

            System.out.println("                         ");}
    }

    public static void main(String[] args) throws IllegalAccessException, IOException, InstantiationException {
        List<BenchmarkCase> bCaseList=initCase();
        int k=1;
        for(BenchmarkCase bCase:bCaseList)
        {System.out.println("第"+k+"组用例");
        k++;
            runCase(bCase);}


//用于输出List数组
        for(int i=0;i<bCaseList.size();i++)
            System.out.println("List"+i+":"+"  "+bCaseList.get(i));
    }}

    */










     /*verson7:自动加载case类*/
     package com.bitstudy;
             import com.bitstudy.annotations.Benchmark;
             import com.bitstudy.annotations.Measurement;
             import com.bitstudy.cases.StringConcat;
             import javax.swing.text.StringContent;
             import java.io.IOException;
             import java.lang.reflect.InvocationTargetException;
             import java.lang.reflect.Method;
             import java.lang.annotation.Annotation;
             import java.util.ArrayList;
             import java.util.Iterator;
             import java.util.List;
             import java.util.ListIterator;
             import java.util.logging.MemoryHandler;

public class Runner {
    public static List<BenchmarkCase> initCase() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        return new BCaseLoader().load();
    }



   private static void runMethodCase(BenchmarkCase bcase, Method method,int countPerGroup)
    {
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
    }

    public static void runCase(BenchmarkCase bCase)
    {Method[]methods=bCase.getClass().getMethods();int i=1;
        for(Method method:methods)
        {
            Annotation benchmark=  method.getAnnotation(Benchmark.class);
            if(benchmark==null){continue; }

            //int iteration=10;
            //int countPerGroup=1000;//默认的default如果写了属性值可以不写
            /*不过需要初始化*/
            int countPerGroup=0;
            Measurement measurement=(Measurement) method.getAnnotation(Measurement.class);
            if(measurement!=null)
            { countPerGroup=measurement.countPerGroup();}
            System.out.println("方法"+i+"测试性能");
            i++;
            runMethodCase(bCase,method,countPerGroup);

            System.out.println("                         ");}
    }

    public static void main(String[] args) throws IllegalAccessException, IOException, InstantiationException, ClassNotFoundException {
        List<BenchmarkCase> bCaseList=initCase();
        int k=1;
        for(BenchmarkCase bCase:bCaseList)
        {System.out.println("第"+k+"组用例");
            k++;
            runCase(bCase);}

//用于输出List数组
        for(int i=0;i<bCaseList.size();i++)
            System.out.println("List"+i+":"+"  "+bCaseList.get(i));
    }}
