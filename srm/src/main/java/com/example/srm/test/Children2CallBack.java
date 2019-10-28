package com.example.srm.test;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public class Children2CallBack  implements AsyncCallback.Children2Callback {
    @Override
    public void processResult(int i, String s, Object o, List<String> list, Stat stat) {
        for(String data:list){
            System.out.println(data);
        }
        System.out.println("访问路径:"+s);
        System.out.println((String)o);
        System.out.println(stat.toString());
    }
}
