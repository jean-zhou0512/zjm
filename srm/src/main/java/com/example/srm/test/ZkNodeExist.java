package com.example.srm.test;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class ZkNodeExist  implements Watcher {

    public ZooKeeper zk=null;
    public static final String zkServerPath= "47.102.197.42:2183";
    public static final Integer timeout= 5000;
    public ZkNodeExist(String zkServerPath){
        try{
            zk=new ZooKeeper(zkServerPath,timeout,new ZkNodeExist());
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
    public ZkNodeExist(){};

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public static Stat stat=null;
    public static CountDownLatch countDownLatch=new CountDownLatch(1);
    public static void main(String[] args){
        ZkNodeExist zkne=new ZkNodeExist(zkServerPath);
        try{
             stat=zkne.getZk().exists("/test",true);
            if(stat!=null){
                System.out.println("节点版本信息为:"+stat.getVersion());
            }else{
                System.out.println("该节点不存在........");
            }
            countDownLatch.await();
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        try{
            if(watchedEvent.getType()== Event.EventType.NodeCreated){
                System.out.println("节点创建");
                countDownLatch.countDown();
            }else if(watchedEvent.getType()== Event.EventType.NodeDataChanged){
                System.out.println("节点数据改变");
                countDownLatch.countDown();
            }else if(watchedEvent.getType()== Event.EventType.NodeDeleted){
                System.out.println("节点删除");
                countDownLatch.countDown();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
