package com.example.srm.curatorTest;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.retry.RetryUntilElapsed;

public class CuratorOperator {
    public CuratorFramework client = null;
    public static final String zkServerPath="47.102.197.42:2183";

    /**
     * 实例化zk客户端
     */
    public CuratorOperator(){
        /**
         * 同步创建zk示例,原生api是异步的
         *
         * curator链接zookeeper的策略：ExponentialBackoffRetry
         * baseSleepTimeMs:初始sleep的时间
         * maxRetries:最大重试次数
         * maxSleepMs:最大重试时间
         */
        RetryPolicy retryPolicy =  new ExponentialBackoffRetry(1000,5);

        /**
         * curator链接zookeeper的策略：RetryNTimes
         * n:重试的次数
         * sleepMsBetweenRetries:每次重试间隔的时间
         */
        RetryPolicy retryPolicy1 = new RetryNTimes(3,5000);

        /**
         * curator链接zookeeper的策略：RetryOneTime
         * sleepMsBetweenRetry:每次重试间隔的时间
         */
        RetryPolicy retryPolicy2 = new RetryOneTime(3000);

        /**
         * 永远重试,不推荐使用
         */
//        RetryPolicy retryPolicy3=new RetryForever(3000);

        /**
         * curator链接zookeeper的策略：RetryUntilElapsed
         * maxElapsedTimeMs:最大重试时间
         * sleepMsBetweenRetries:每次重试间隔
         * 重试时间超过maxElapsedTimeMs后，就不再重试
         */
        RetryPolicy retryPolicy3=new RetryUntilElapsed(2000,3000);

        client= CuratorFrameworkFactory.builder()
                .connectString(zkServerPath)
                .sessionTimeoutMs(10000)
                .retryPolicy(retryPolicy)
                .namespace("workspace")
                .build();
        client.start();
    }

    /**
     * 关闭zk客户端连接
     */
    public void closeZkClient(){
        if(client !=null){
            this.client.close();
        }
    }

    public static void main(String[] args) {
        try{
            CuratorOperator curatorOperator=new CuratorOperator();
            boolean isStatrt=curatorOperator.client.isStarted();
            System.out.println("当前客户的状态"+(isStatrt?"连接中" : "已关闭"));

            String path="/nodetest";
         /*   byte [] data="aa".getBytes();
            curatorOperator.client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(path,data);*/


      /*      curatorOperator.client
                    .setData()
                    .withVersion(0)
                    .forPath(path,"batman".getBytes());*/

          /*  curatorOperator.client.delete()
                    .guaranteed()
                    .deletingChildrenIfNeeded()
                    .withVersion(0)
                    .forPath(path);*/

          /*  Stat stat=new Stat();
          byte [] data=curatorOperator.client.getData()
                  .storingStatIn(stat)
                  .forPath(path);
          System.out.println(path+"节点数据为:"+new String(data));
          System.out.println(path+"节点的版本号为:"+stat.getVersion());*/


        /*  List<String> nodeList=curatorOperator.client.getChildren().forPath(path);
          for(String node : nodeList){
              System.out.println(node);
          }*/

      /*  Stat isExistNode=curatorOperator.client.checkExists().forPath(path);
           if(isExistNode ==null){
               System.out.println("该节点不存在!!");
           }else{
               System.out.println("该节点信息为:"+isExistNode);
           }*/
//           curatorOperator.client.getData().usingWatcher((new MyCuratorWatch())).forPath(path);
//           curatorOperator.client.getData().usingWatcher((new MyWatch())).forPath(path);

            /*NodeCache nodeCache=new NodeCache(curatorOperator.client,path);

            nodeCache.start(true);

            if(nodeCache != null){
                System.out.println("节点初始化数据为:"+new String(nodeCache.getCurrentData().getData()));
            }else{
                System.out.println("节点数据为空...");
            }

            nodeCache.getListenable().addListener(()->{

                if(nodeCache ==null){
                    System.out.println("节点被删除...");
                }else {
                    String data=new String(nodeCache.getCurrentData().getData());
                    System.out.println("节点路径:"+nodeCache.getCurrentData().getPath()+"数据:"+data);
                }

                    *//*() {
                @Override
                public void nodeChanged() throws Exception {

                }*//*
            });*/

            /**
             * StartMode:初始化方式
             *  BUILD_INITIAL_CACHE:同步初始化
             *  NORMAL：异步初始化
             *  POST_INITIALIZED_EVENT：异步初始化，初始化之后会触发事件
             */

            PathChildrenCache pathChildrenCache=new PathChildrenCache(curatorOperator.client,path,true);
            pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

          /*  List<ChildData> childData=pathChildrenCache.getCurrentData();
            for(ChildData data : childData){
                String dataStr=new String(data.getData());
                System.out.println(dataStr);
            }*/

          pathChildrenCache.getListenable().addListener( ((client1, event) -> {
              if(event.getType().equals(PathChildrenCacheEvent.Type.INITIALIZED)){
                  System.out.println("子节点初始化成功...");
              }else if(event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)){
                  System.out.println("子节点创建成功,节点路径为："+event.getData().getPath());
                  System.out.println("子节点数据为："+new String(event.getData().getData()));
              }else if(event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)){
                  System.out.println("子节点数据更新成功,节点路径为："+event.getData().getPath());
                  System.out.println("子节点数据为:"+new String(event.getData().getData()));
              }else if(event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)){
                  System.out.println("子节点"+event.getData().getPath()+"被删除...");
              }
              
          }) );

            Thread.sleep(1000000);

            curatorOperator.closeZkClient();

            boolean isStop=curatorOperator.client.isStarted();
            System.out.println("当前客户的状态"+(isStop?"连接中" : "已关闭"));



        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
