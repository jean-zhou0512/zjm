package com.example.srm.test;

import org.apache.zookeeper.AsyncCallback;

import java.util.List;

public class ChildrenCallBack implements AsyncCallback.ChildrenCallback 
{
    @Override
    public void processResult(int i, String s, Object o, List<String> list) {
        for(String data:list){
            System.out.println(data);
        }
        System.out.println("访问路径:"+s);
        System.out.println((String)o);
    }
}
