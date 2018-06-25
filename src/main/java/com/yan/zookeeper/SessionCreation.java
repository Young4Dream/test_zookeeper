package com.yan.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class SessionCreation implements Watcher {
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    public static void main(String[] args) throws IOException, InterruptedException {
        SessionCreation watcher = new SessionCreation();
        ZooKeeper zooKeeper = new ZooKeeper("master:2181,slave:2181,slave1:2181", 5000, watcher);
        watcher.countDownLatch.await();
        long sessionId = zooKeeper.getSessionId();
        System.out.println(sessionId);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getType() == Event.EventType.None) {
            countDownLatch.countDown();
            System.out.println("ok");
        }
    }
}
