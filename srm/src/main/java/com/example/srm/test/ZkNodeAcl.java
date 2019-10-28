package com.example.srm.test;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZkNodeAcl {

    public ZooKeeper zk=null;
    public static final String zkServerPath= "47.102.197.42:2183";
    public static final Integer timeout= 5000;

    public ZkNodeAcl(){};

    public ZkNodeAcl(String zkServerPath){
        try{
            zk=new ZooKeeper(zkServerPath,timeout,null);
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
    public static void main(String[] args)throws IOException, InterruptedException, KeeperException, Exception {
        ZkNodeAcl zkna=new ZkNodeAcl(zkServerPath);
//        zkna.createNode("/test","test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);

       /*   List<ACL> acls=new ArrayList<ACL>();
          Id zjm1=new Id("digest",AclUtils.getDigestUserPwd("zjm1:123456"));
          Id zjm2=new Id("digest",AclUtils.getDigestUserPwd("zjm2:123456"));
          acls.add(new ACL(ZooDefs.Perms.ALL,zjm1));
          acls.add(new ACL(ZooDefs.Perms.READ,zjm2));
          acls.add(new ACL(ZooDefs.Perms.CREATE | ZooDefs.Perms.DELETE ,zjm2));*/

//          zkna.createNode("/test/aa","aa".getBytes(),acls);
   /*     zkna.getZk().addAuthInfo("digest","zjm2:123456".getBytes());
//        zkna.createNode("/test/aa/bb","bb".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);
//        System.out.println("创建节点成功");
//        zkna.getZk().setData("/test/aa","123".getBytes(),0);
        Stat stat=new Stat();
        byte [] data=zkna.getZk().getData("/test/aa",false,stat);
        System.out.println(new String(data));*/

      /*  List<ACL> acls=new ArrayList<>();
        Id ip=new Id("ip","192.168.200.192");
        acls.add(new ACL(ZooDefs.Perms.ALL,ip));
        zkna.createNode("/ip1","ip1".getBytes(),acls);*/

       /* List<ACL> acls=new ArrayList<>();
        Id ip=new Id("ip","47.102.197.42");
        acls.add(new ACL(ZooDefs.Perms.ALL,ip));
        zkna.createNode("/ip2","ip2".getBytes(),acls);*/

       /* List<ACL> acls=new ArrayList<>();
        Id ip=new Id("ip","192.168.200.254");
        acls.add(new ACL(ZooDefs.Perms.ALL,ip));
        zkna.createNode("/ip3","ip3".getBytes(),acls);*/

      /*  List<ACL> acls=new ArrayList<>();
        Id ip=new Id("ip","172.19.115.212");
        acls.add(new ACL(ZooDefs.Perms.ALL,ip));
        zkna.createNode("/ip4","ip4".getBytes(),acls);*/

        /*List<ACL> acls=new ArrayList<>();
        Id ip=new Id("ip","172.17.0.1");
        acls.add(new ACL(ZooDefs.Perms.ALL,ip));
        zkna.createNode("/ip5","ip5".getBytes(),acls);*/
      /*  List<ACL> acls=new ArrayList<>();
        Id ip=new Id("ip","0.0.0.0");
        acls.add(new ACL(ZooDefs.Perms.ALL,ip));
        zkna.createNode("/ip6","ip6".getBytes(),acls);*/

  /*      List<ACL> acls=new ArrayList<>();
        Id ip=new Id("ip","127.0.0.1");
        acls.add(new ACL(ZooDefs.Perms.ALL,ip));
        zkna.createNode("/ip7","ip7".getBytes(),acls);*/

      /*  List<ACL> acls=new ArrayList<>();
        Id ip=new Id("ip","192.168.200.254");
        acls.add(new ACL(ZooDefs.Perms.ALL,ip));
        zkna.createNode("/ip8","ip8".getBytes(),acls);

*/

     /*   List<ACL> acls=new ArrayList<>();
        Id ip=new Id("ip","113.109.76.122");
        acls.add(new ACL(ZooDefs.Perms.ALL,ip));
        zkna.createNode("/ip9","ip9".getBytes(),acls);*/

        Stat stat=new Stat();
        byte [] data=zkna.getZk().getData("/ip9",false,stat);
        System.out.println(new String(data));


    }

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public  void createNode(String path,byte[] data ,List<ACL> acls){
        String result="";
        try{
            result=zk.create(path,data,acls, CreateMode.PERSISTENT);
            System.out.println("创建节点: \t"+result+"\t成功...");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
