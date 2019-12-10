package com.example.srm.test;

import org.apache.zookeeper.AsyncCallback;

public class VoidCallBack implements AsyncCallback.VoidCallback {
    @Override
    public void processResult(int i, String s, Object o) {
        System.out.println("删除节点:"+s);
        System.out.println((String)o);
    }
}
