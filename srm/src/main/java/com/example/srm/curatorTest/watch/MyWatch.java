package com.example.srm.curatorTest.watch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class MyWatch  implements Watcher {
    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("监控节点路径为:"+watchedEvent.getPath());
    }
}
