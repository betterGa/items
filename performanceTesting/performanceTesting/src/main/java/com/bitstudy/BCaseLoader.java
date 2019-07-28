package com.bitstudy;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class BCaseLoader {
    List<BenchmarkCase> load() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        List<String> bCaseClassNameList = scanBCaseClassNameList();
        List<BenchmarkCase> bCaseList = buildBCaseList(bCaseClassNameList);
        System.out.printf("共加载%d个基准测试case%m%m", bCaseList.size());
        return bCaseList;
    }

    private List<String> scanBCaseClassNameList() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        List<String> classNameList = new ArrayList<String>();
        try {
          Enumeration<URL>  urls=classLoader.getResources("com/bitstudy/cases");
          while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (!url.getProtocol().equals("file"))
                { continue; }
              else{  classNameList.addAll(scanClassesFromFile(URLDecoder.decode(url.getPath(), "UTF-8"))); }

        }}
            catch (IOException e) {
         return classNameList;
        }return classNameList;
    }

    private List<String> scanClassesFromFile(String path) {
        File dir = new File(path);
        List<String> classNameList = new ArrayList<String>();
        File[] files = dir.listFiles();
        if (files == null) {
            return classNameList;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }  //如果是目录，退出
            String filename = file.getName();
            if (!filename.endsWith(".class")) {
                continue;
            }
            if (filename.lastIndexOf("$") != -1) {
                continue;
            }
            String name = filename.substring(0, filename.length() - 6);
            classNameList.add("com.bitstudy.cases" + name);
        }
        return classNameList;
    }

    private List<BenchmarkCase> buildBCaseList(List<String> bCaseClassNameList)
    {
        List<BenchmarkCase> bCaseList = new ArrayList<BenchmarkCase>(bCaseClassNameList.size());
        for (String className : bCaseClassNameList) {
            try {
                Class<?>bCaseClass=Class.forName(className);
                if (!isBcaseClass(bCaseClass))
                { continue; }
                BenchmarkCase bCase = (BenchmarkCase) bCaseClass.newInstance();
                bCaseList.add(bCase);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

catch (InstantiationException e){e.printStackTrace();}
catch (IllegalAccessException e){e.printStackTrace();}
        }
        return bCaseList; }

    private boolean isBcaseClass(Class<?> bCaseClass) {
        return BenchmarkCase.class.isAssignableFrom(bCaseClass);
    }
}