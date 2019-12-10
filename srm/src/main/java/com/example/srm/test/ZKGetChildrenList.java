package com.example.srm.test;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZKGetChildrenList  implements Watcher {
    public ZooKeeper zk=null;
    public static final String zkServerPath= "47.102.197.42:2183";
    public static final Integer timeout= 5000;

    public ZKGetChildrenList(){};

    public ZKGetChildrenList(String zkServerPath){
        try{
            zk=new ZooKeeper(zkServerPath,timeout,new ZooCreateTest());
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

    public static CountDownLatch cdl=new CountDownLatch(1);
    public static void main(String[] args){
        try{
            ZKGetChildrenList zkgcl=new ZKGetChildrenList(zkServerPath);
         /*   List<String> childList= zkgcl.getZk().getChildren("/data",new ZKGetChildrenList());
            for(String data:childList){
                System.out.println(data);
            }*/
            //异步调用
            String ctx="{'callback':'success'}";
//            zkgcl.getZk().getChildren("/data",new ZKGetChildrenList(),new ChildrenCallBack(),ctx);
            zkgcl.getZk().getChildren("/data",new ZKGetChildrenList(),new Children2CallBack(),ctx);
            cdl.await();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        try{
            if(watchedEvent.getType() == Event.EventType.NodeChildrenChanged){
                System.out.println("NodeChildrenChanged");
                ZKGetChildrenList zkgcl=new ZKGetChildrenList(zkServerPath);
               List<String> dataList= zkgcl.getZk().getChildren(watchedEvent.getPath(),false);
               for(String data:dataList ){
                   System.out.println(data);
               }
               cdl.countDown();
            }else if(watchedEvent.getType() == Event.EventType.NodeDeleted){
                System.out.println("NodeDeleted");
            }else if(watchedEvent.getType() == Event.EventType.NodeCreated){
                System.out.println("NodeCreated");
            }else if(watchedEvent.getType() == Event.EventType.NodeDataChanged){
                System.out.println("NodeDataChanged");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }
}
