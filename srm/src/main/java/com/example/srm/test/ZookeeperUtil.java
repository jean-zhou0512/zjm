package com.example.srm.test;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperUtil {
    public ZooKeeper zk=null;
    public static final String zkServerPath= "47.102.197.42:2183";
    public static final Integer timeout= 5000;
    public static Stat stat=new Stat();
    public ZookeeperUtil(String zkServerPath){
        try{
            zk=new ZooKeeper(zkServerPath,timeout,new ZKGetNodeData());
        }catch (Exception e){
            if(zk!=null){
                try{
                    zk.close();
                }catch (Exception c){
                    c.printStackTrace();
                }
            }
        }
    }

    public ZookeeperUtil(){};

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }
}
