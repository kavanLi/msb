package com.msb.db;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 类说明：数据库连接池简单实现，线程安全，限定了连接池为2个大小
 */
@Component
public class DBPool {

	//数据库池的容器
	private static LinkedList<Connection> pool = new LinkedList<>();
	//线程安全，限定了连接池为2个大小
	private final static int CONNECT_CONUT = 2;
	static{
		for(int i=0;i<CONNECT_CONUT;i++) {
			pool.addLast(SqlConnectImpl.fetchConnection());
		}
	}

	//在mills时间内还拿不到数据库连接，返回一个null
	public Connection fetchConn(long mills) throws InterruptedException {
		synchronized (pool) {
			if (mills<0) {
				while(pool.isEmpty()) {
					pool.wait();
				}
				return pool.removeFirst();
			}else {
				long overtime = System.currentTimeMillis()+mills;
				long remain = mills;
				while(pool.isEmpty()&&remain>0) {
					pool.wait(remain);
					remain = overtime - System.currentTimeMillis();
				}
				Connection result  = null;
				if(!pool.isEmpty()) {
					result = pool.removeFirst();
				}
				return result;
			}
		}
	}

	//放回数据库连接
	public void releaseConn(Connection conn) {
		if(conn!=null) {
			synchronized (pool) {
				pool.addLast(conn);
				pool.notifyAll();
			}
		}
	}


}
