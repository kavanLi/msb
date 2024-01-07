package com.bobo.test;

import jdk.nashorn.internal.ir.CallNode;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class Test1 {

    private String connectString = "192.168.100.121:2181,192.168.122:2181,192.168.100.122:2181";

    private int sessionTimeOut = 10000;

    ZooKeeper zooKeeper = null;

    /**
     * 连接Zookeeper服务端
     */
    @Before
    public void test1() throws IOException {
        zooKeeper = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            /**
             * 触发监听事件的回调方法
             * @param watchedEvent
             */
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("触发了.....");
            }
        });
        //System.out.println("--->" + zooKeeper);
    }

    /**
     * 创建节点
     */
    @Test
    public void createNode() throws Exception{
        String path = zooKeeper.create("/apptest" // 节点路径
                ,"HelloZookeeper".getBytes() // 节点的数据
                , ZooDefs.Ids.OPEN_ACL_UNSAFE // 权限
        , CreateMode.PERSISTENT // 节点类型
        );
        System.out.println(path);
    }

    /**
     * 判断节点是否存在
     */
    @Test
    public void exist() throws  Exception{
        // true表示的是使用Zookeeper中的watch
        Stat stat = zooKeeper.exists("/apptest", true);
        if(stat != null){
            System.out.println("节点存在"+ stat.getNumChildren());
        }else{
            System.out.println("节点不存在 ....");
        }
    }

    /**
     * 获取某个节点下面的所有的子节点
     */
    @Test
    public void getChildrens() throws Exception{
        List<String> childrens = zooKeeper.getChildren("/app1", true);
        for (String children : childrens) {
            // System.out.println(children);
            // 获取子节点中的数据
            byte[] data = zooKeeper.getData("/app1/" + children, false, null);
            System.out.println(children+":" + new String(data));
        }
    }

    /**
     * 修改节点的内容
     */
    @Test
    public void setData() throws Exception{
        // -1 不指定版本 自动维护
        Stat stat = zooKeeper.setData("/app1/a1", "666666".getBytes(), -1);
        System.out.println(stat);
    }


    /**
     * 删除节点
     */
    @Test
    public void deleteNode() throws Exception{
        zooKeeper.delete("/app1",-1);

    }


    /**
     * 监听Node节点下的子节点的变化
     */
    @Test
    public void nodeChildrenChange() throws Exception{
        List<String> list = zooKeeper.getChildren("/app1", new Watcher() {

            /**
             *              None(-1),
             *             NodeCreated(1),
             *             NodeDeleted(2),
             *             NodeDataChanged(3),
             *             NodeChildrenChanged(4),
             *             DataWatchRemoved(5),
             *             ChildWatchRemoved(6);
             * @param watchedEvent
             */
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("--->"+ watchedEvent.getType());
            }
        });
        for (String s : list) {
            System.out.println(s);
        }

        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 监听节点内容变更
     */
    @Test
    public void nodeDataChanged() throws Exception{
        byte[] data = zooKeeper.getData("/app1/a1", new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("--->" + watchedEvent.getType());
            }
        }, null);
        System.out.println("--->" + new String(data));
        Thread.sleep(Integer.MAX_VALUE);
    }


}
