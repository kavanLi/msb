package cn.edu.db;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 类说明：数据库连接池手动实现，限定了连接池为20个大小，如果超过了则返回null
 */
@Component
public class DBPool {

	//数据库池的容器
	private static LinkedList<Connection> pool = new LinkedList<>();
	//限定了连接池为20个大小
	private final static int CONNECT_CONUT = 20;
	static{
		for(int i=0;i<CONNECT_CONUT;i++) {
			pool.addLast(SqlConnectImpl.fetchConnection());
		}
	}

	//在mills时间内还拿不到数据库连接，返回一个null,超时机制
	public Connection fetchConn(long mills) throws InterruptedException {
		synchronized (pool) {
			if (mills<0) {//没有时间限制
				while(pool.isEmpty()) {//如果连接池为空，则等待
					pool.wait();
				}
				return pool.removeFirst();
			}else {//有时间限制
				long overtime = System.currentTimeMillis()+mills;
				long remain = mills;
				while(pool.isEmpty()&&remain>0) {//如果连接池为空，则等待
					pool.wait(remain);
					remain = overtime - System.currentTimeMillis();
				}
				Connection result  = null;
				if(!pool.isEmpty()) {//如果连接池不为空，则取出连接
					result = pool.removeFirst();
				}
				return result;
			}
		}
	}

	//放回数据库连接,通知其他等待线程
	public void releaseConn(Connection conn) {
		if(conn!=null) {
			synchronized (pool) {
				pool.addLast(conn);
				pool.notifyAll();
			}
		}
	}


}
