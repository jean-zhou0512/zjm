package com.example.srm.test;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;

import java.util.List;

public class ZooCreateTest  implements Watcher {
    public ZooKeeper zk=null;
    public static final String zkServerPath= "47.102.197.42:2183";
    public static final Integer timeout= 5000;
    public ZooCreateTest(String zkServerPath){
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
    public ZooCreateTest(){};

    @Override
    public void process(WatchedEvent watchedEvent) {
    }

    public static void main(String[] args){
       ZooCreateTest zct=new ZooCreateTest(zkServerPath);
        /*  zct.CreateNode("/testnode","testnode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);*/
        /**
         * update 节点
         */
      /* try{
           Stat status=zct.getZk().setData("/testnode","update".getBytes(),0);
           System.out.println(status.getVersion());
       }catch (Exception e){
           e.printStackTrace();
       }*/
        /**
         * delete 节点
         */
        zct.CreateNode("/test-delete","test-delete".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);

        try{
            String ctx="{'delete':'success'}";
            zct.getZk().delete("/test-delete",0,new VoidCallBack(),ctx);
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public void CreateNode(String path, byte[] data, List<ACL> acls){
       String result="";
        try{
            /**
             * EPHEMERAL:临时节点
             * PERSISTENT：永久节点
             */
            String ctx ="{'create':'success'}";
//             result=zk.create(path,data,acls, CreateMode.EPHEMERAL);
            zk.create(path,data,acls,CreateMode.PERSISTENT,new CreateCallBack(),ctx);
            System.out.println("创建节点: \t"+result+"\t成功....");
            new Thread().sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
