/*version 1:最简化版本



package com.bitstudy.cases;
public class StringConcat {
    private static String addString() {
        String a = "";
        for (int i = 0; i < 10; i++) {
            a += i;//在String中使用“+”来进行字符串连接
        }
        return a;
    }

    private static String addStringBuilder() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i); //StringBuffer类中需要用append（）方法}
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        long t1=System.nanoTime();
        addStringBuilder();
        long t2=System.nanoTime();
        addString();
        long t3=System.nanoTime();
        System.out.println("StringBuilder耗时："+(t2-t1));
        System.out.println("String       耗时："+(t3-t2));

    }
}



*/





/*version2:进行多次测试
package com.bitstudy.cases;
public class StringConcat
{
    private static String addString() {
        String a = "";
        for (int i = 0; i < 10; i++) {
            a += i;//在String中使用“+”来进行字符串连接
        }
        return a;
    }

    private static String addStringBuilder() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i); //StringBuffer类中需要用append（）方法}
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        for(int i=0;i<10;i++)
        {long t1=System.nanoTime();
        addString();
        long t2=System.nanoTime();
        addStringBuilder();
        long t3=System.nanoTime();
            System.out.printf("第%d次测试：String       耗时:%d%n",i+1,t2-t1);
            System.out.printf("第%d次测试：StringBuilder耗时:%d%n",i+1,t3-t2);
        }


    }


}
*/



/*version3:增减单次实验次数


package com.bitstudy.cases;
public class StringConcat
{
    private static String addString() {
        String a = "";
        for (int i = 0; i < 10; i++) {
            a += i;//在String中使用“+”来进行字符串连接
        }
        return a;
    }

    private static String addStringBuilder() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i); //StringBuffer类中需要用append（）方法}
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        for(int i=0;i<10;i++) {
            for (int j = 0; j < 1000; j++) {
                long t1 = System.nanoTime();
                addString();
                long t2 = System.nanoTime();
                addStringBuilder();
                long t3 = System.nanoTime();}
                System.out.printf("第%d次测试：String       耗时:%d%n", i + 1, t2 - t1);
                System.out.printf("第%d次测试：StringBuilder耗时:%d%n", i + 1, t3 - t2);

        }
        }
    }
*/




/*复习注解。  注解：将配置写回程序中。

package com.bitstudy.cases;

    class Person
    {@Override
    public String toString()
    {return "ohh";}}
    public class StringConcat
    {public static void main(String[] args) {

        System.out.println(new Person().toString());
    }}
    //运行结果为：ohh

 */




/*version4:预热功能，待完成。

 */





/*
package com.bitstudy.cases;
import com.bitstudy.annotations.Measurement;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringConcat {

    @Measurement(iteration = 10,countPerGroup = 1)
    public static String addString() {
        String a = "";
        for (int i = 0; i < 10; i++) {
            a += i;//在String中使用“+”来进行字符串连接
        }
        return a;
    }
    @Measurement(iteration = 10,countPerGroup = 1)
    public static String addStringBuilder() {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i); //StringBuffer类中需要用append（）方法}
        }
        return sb.toString();
    }

    public static void runCase(Method method,int iterations,int countPerGroup)  {
for(int i=0;i<iterations;i++)
{
    System.out.printf("第%d次测试",i+1);
    long t1=System.nanoTime();
    for(int j=0;j<countPerGroup;j++)
    {
        try {
            method.invoke(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    long t2=System.nanoTime();
    System.out.printf("耗时：%d%n",t1-t2);

} }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
StringConcat stringConcat=new StringConcat();
 Method[]methods=StringConcat.class.getMethods();
        for(Method method:methods)
        {
            if(method.getName().equals("addString")||method.getName().equals("addStringBuilder"))
            {Annotation annotation=method.getAnnotation(Measurement.class);
            int iterations=10;
            int countPerGroup=1000;
            if(annotation!=null)//如果有该方法注解
            {Measurement measurement=(Measurement) annotation;
            iterations=measurement.iteration();
            countPerGroup=measurement.countPerGroup(); }
runCase(method,iterations,countPerGroup);
            }
        } }
}

*/


package com.bitstudy.cases;

import com.bitstudy.BenchmarkCase;
import com.bitstudy.annotations.Benchmark;
import com.bitstudy.annotations.Measurement;

public class StringConcat implements BenchmarkCase {
    @Measurement(iteration = 10, countPerGroup = 3)
    @Benchmark
    public String addString() {
        String a = "";
        for (int i = 0; i < 10; i++) {
            a = a + i;
        }
        return a;
    }

    @Measurement
    @Benchmark
    public String addStringBuffer() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }
        return sb.toString();
    }
}









