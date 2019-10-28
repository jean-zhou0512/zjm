package com.example.srm.test;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZooTest implements Watcher {
    final static Logger log= LoggerFactory.getLogger(ZooTest.class);
    public static final String zkServerPath= "47.102.197.42:2181";
//    public static final String zkServerPath="47.102.197.42:2181,47.102.197.42:2182,47.102.197.42:2183";
    public static final Integer timeout=5000;

    public static void main(String[] args){
        try{
            ZooKeeper zk=new ZooKeeper(zkServerPath,timeout,new ZooTest());
            log.info("客户端开始连接zookeeper服务器...");
            log.info("连接状态:{}",zk.getState());

            new Thread().sleep(2000);

            log.info("连接状态:{}",zk.getState());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void process(WatchedEvent watchedEvent) {
        log.info("接受到watch通知:{}",watchedEvent);
    }

    @Test
    public void zooTest1(){
        try{
            ZooKeeper zk=new ZooKeeper(zkServerPath,timeout,new ZooTest());
            log.info("客户端开始连接zookeeper服务器...");
            log.info("连接状态:{}",zk.getState());
            new Thread().sleep(2000);
            Long ssid=zk.getSessionId();
            byte[] password =zk.getSessionPasswd();
            log.info("连接状态:{}",zk.getState());

            log.info("开始重新连接zookeeper服务器...");
            ZooKeeper zk1=new ZooKeeper(zkServerPath,timeout,new ZooTest(),ssid,password);
            log.info("重新连接状态:{}",zk.getState());
            new Thread().sleep(2000);
            log.info("重新连接状态:{}",zk.getState());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
