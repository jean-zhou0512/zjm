package com.example.srm.test;

import org.apache.zookeeper.AsyncCallback;

public class CreateCallBack implements AsyncCallback.StringCallback {
    @Override
    public void processResult(int i, String s, Object o, String s1) {
        System.out.println("创建节点:"+s);
        System.out.println((String)o);
    }
}
