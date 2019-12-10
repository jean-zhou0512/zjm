package com.example.srm.curatorTest;

import com.example.srm.pojo.RedisConfig;
import com.example.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;

public class Cline3 {
    public static final String zkServicePath="47.102.197.42:2183";
    public CuratorFramework cline=null;

    public Cline3(){
        RetryPolicy retryPolicy=new ExponentialBackoffRetry(1000,5);
        cline= CuratorFrameworkFactory.builder()
                .connectString(zkServicePath)
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(10000)
                .namespace("workspace")
                .build();
        cline.start();
    }

    public void closeZKClient(){
        if (cline!=null){
            this.cline.close();
        }
    }

    public  final static String CONFIG_NODE_PATH="/super/imooc";
    public final static String SUB_PATH="/redis-config";
    public static CountDownLatch countDown=new CountDownLatch(1);
    public static void main(String[] args) {
        try {
            Cline1 cline1 = new Cline1();
            System.out.println("client1启动成功...");

            PathChildrenCache pathChildrenCache = new PathChildrenCache(cline1.cline, CONFIG_NODE_PATH, true);
            pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);

            pathChildrenCache.getListenable().addListener(((client, event) -> {
                if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
                    String configNodepath = event.getData().getPath();
                    if (configNodepath.equals(CONFIG_NODE_PATH+SUB_PATH)) {
                        System.out.println("监听到配置发生变化，节点路径为:"+configNodepath);

                        //读取节点数据
                        String data = new String(event.getData().getData());
                        System.out.println("节点"+CONFIG_NODE_PATH+SUB_PATH+"的数据为:"+data);

                        RedisConfig redisConfig = null;
                        if(StringUtils.isNotBlank(data)){
                            redisConfig= JsonUtils.jsonToPojo(data,RedisConfig.class);
                        }

                        //配置不为空进行的操作
                        if(redisConfig!=null){
                            String type=redisConfig.getType();
                            String url=redisConfig.getUrl();
                            String remark=redisConfig.getRemark();

                            if(type.equals("add")){
                                System.out.println("监听到新增的配置，准备下载...");
                                //...连接ftp服务器，根据url找到相应的配置
                                Thread.sleep(500);
                                System.out.println("开始下载新的配置文件,下载路径为<"+url+">");
                                //下载配置到你指定的目录
                                Thread.sleep(1000);
                                System.out.println("下载成功,已经添加项目中");
                                //..拷贝文件到项目目录
                            }else if(type.equals("update")){
                                System.out.println("监听到更新的配置，准备下载...");
                                //...连接ftp服务器,根据url找到相应的配置
                                Thread.sleep(500);
                                System.out.println("开始下载配置文件,下载路径为<"+url+">");
                                //...下载配置到你指定的目录
                                Thread.sleep(1000);
                                System.out.println("下载成功...");
                                System.out.println("删除项目中原配置文件...");
                                Thread.sleep(100);
                                //删除原文件
                                System.out.println("拷贝配置文件到项目目录...");
                                //...拷贝文件到项目目录
                            }else if(type.equals("delete")){
                                System.out.println("监听到需要删除配置");
                                System.out.println("删除项目中原配置文件...");
                            }
                        }

                    }
                }

            }));
            countDown.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
