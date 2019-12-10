package com.example.srm;

import java.util.ArrayList;
import java.util.List;

public class test01 {
    
    public static void main(String[] args){
        List<String> a=new ArrayList<>();
        a.add("a");
        a.add("b");
        a.add("c");
        a.add("d");
        for(String str:a){
            System.out.println("list输出: "+str);
        }
//      双冒号输出
        a.forEach(System.out::println);

//        使用lambda表达式以及函数操作
        a.forEach((s -> System.out.println("lambda输出: "+s)));


    }
}
