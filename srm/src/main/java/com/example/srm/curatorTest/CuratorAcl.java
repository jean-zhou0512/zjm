package com.example.srm.curatorTest;

import com.example.utils.AclUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;

public class CuratorAcl {
    public static final String zkServicePath="47.102.197.42:2181";
    public CuratorFramework cline=null;

    public CuratorAcl(){
        RetryPolicy retryPolicy=new ExponentialBackoffRetry(1000,5);
        cline= CuratorFrameworkFactory.builder().authorization("digest","zjm1:123456".getBytes())
                .connectString(zkServicePath)
                .sessionTimeoutMs(10000)
                .retryPolicy(retryPolicy)
                .namespace("workspace")
                .build();
        cline.start();
    }

    public void closeZkCline(){
        if(cline!=null){
            cline.close();
        }
    }

    public static void main(String[] args){
        String path="/zjm";
        try{
            List<ACL> acls=new ArrayList<>();
            Id zjm1=new Id("digest", AclUtils.getDigestUserPwd("zjm1:123456"));
            Id zjm2=new Id("digest",AclUtils.getDigestUserPwd("zjm2:123456"));
         /*    acls.add(new ACL(ZooDefs.Perms.ALL,zjm1));
            acls.add(new ACL(ZooDefs.Perms.READ,zjm2));
            acls.add(new ACL((ZooDefs.Perms.CREATE| ZooDefs.Perms.DELETE),zjm2));*/
            acls.add(new ACL((ZooDefs.Perms.ALL),zjm2));

            CuratorAcl curatorAcl=new CuratorAcl();
            boolean isStatrt=curatorAcl.cline.isStarted();
            System.out.println("当前客户的状态"+(isStatrt?"连接中" : "已关闭"));
           /* curatorAcl.cline.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .withACL(acls,true)
                    .forPath(path,"aaa".getBytes());*/

//            curatorAcl.cline.setACL().withACL(acls).forPath(path);

            //更新节点数据
//            curatorAcl.cline.setData().withVersion(0).forPath(path,"test".getBytes());
            //删除节点
//            curatorAcl.cline.delete().guaranteed().deletingChildrenIfNeeded().withVersion(1).forPath(path);

            //获取节点信息
            Stat stat=new Stat();
            byte data[]=curatorAcl.cline.getData().storingStatIn(stat).forPath(path);
            System.out.println("节点"+path+"的数据为:"+new String(data));
            System.out.println("该节点的版本号为:"+stat.getVersion());
            curatorAcl.closeZkCline();
            boolean isStop=curatorAcl.cline.isStarted();
            System.out.println("当前客户的状态"+(isStop?"连接中" : "已关闭"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
