package com.example.srm.test;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class ZKGetNodeData implements Watcher {
    public ZooKeeper zk=null;
    public static final String zkServerPath= "47.102.197.42:2183";
    public static final Integer timeout= 5000;
    public static Stat stat=new Stat();
    public ZKGetNodeData(String zkServerPath){
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

    public ZKGetNodeData(){};

    public static  CountDownLatch cdl=new CountDownLatch(1);
    public static void main(String[] args){
        ZKGetNodeData zknd=new ZKGetNodeData(zkServerPath);
        try{
            byte[] data=zknd.getZk().getData("/testnode",new ZKGetNodeData(),stat);
            String dataStr=new String(data);
            System.out.println("值为:"+dataStr);
            System.out.println("版本号:"+stat.getVersion());
            cdl.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (KeeperException k){
            k.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        try{
        if(watchedEvent.getType() == Event.EventType.NodeDataChanged){
            ZKGetNodeData zkgnd=new ZKGetNodeData(zkServerPath);
               byte[] data=zkgnd.getZk().getData("/testnode",false,stat);
               String dataStr=new String(data);
               System.out.println("更改后的值:"+dataStr);
               System.out.println("更改后的版本:"+stat.getVersion());
               cdl.countDown();
        }else if(watchedEvent.getType()== Event.EventType.NodeChildrenChanged){

        }else if(watchedEvent.getType()==Event.EventType.NodeCreated){

        }else if(watchedEvent.getType()==Event.EventType.NodeDeleted){

        }
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (KeeperException k){
            k.printStackTrace();
        }
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }
}
