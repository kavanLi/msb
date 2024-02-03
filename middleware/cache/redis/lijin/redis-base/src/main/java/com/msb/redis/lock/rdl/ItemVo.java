package com.msb.redis.lock.rdl;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *
 *类说明：存放到延迟队列的元素，比标准的delay的实现要提前一点时间
 */
public class ItemVo<T> implements Delayed{

    /*到期时刻  20:00:35,234*/
    private long activeTime;
    /*业务数据，泛型*/
    private T data;

	/*传入的数值代表过期的时长，单位毫秒，需要乘1000转换为毫秒和到期时间
	* 同时提前100毫秒续期,具体的时间可以自己决定*/
	public ItemVo(long expirationTime, T data) {
		super();
		this.activeTime = expirationTime+System.currentTimeMillis()-100;
		this.data = data;
	}

	public long getActiveTime() {
		return activeTime;
	}

	public T getData() {
		return data;
	}
	
	/**
	 * 返回元素到激活时刻的剩余时长
	 */
	public long getDelay(TimeUnit unit) {
		long d = unit.convert(this.activeTime
						- System.currentTimeMillis(),unit);
		return d;
	}

	/**按剩余时长排序*/
	public int compareTo(Delayed o) {
        long d = (getDelay(TimeUnit.MILLISECONDS)
				-o.getDelay(TimeUnit.MILLISECONDS));
        if (d==0){
        	return 0;
		}else{
        	if (d<0){
        		return -1;
			}else{
        		return  1;
			}
		}
	}

}
