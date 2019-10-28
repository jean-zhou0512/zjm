package com.example.srm.curatorTest.watch;

import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;

public class MyCuratorWatch implements CuratorWatcher {
    @Override
    public void process(WatchedEvent event) throws Exception {
        System.out.println("监控节点路径为:"+event.getPath());
    }
}
