package com.thread_pattern.guarded_suspension_pattern;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用来依次存放请求的类
 * Guarded Object (被防卫的对象)参与者
 * @author tony
 *
 */
public class RequestQueue {

	protected static final Logger log = LoggerFactory.getLogger(RequestQueue.class);


	private final LinkedList<Request> queue = new LinkedList<Request>();

	public synchronized Request getRequest() {
		try {
			while(queue.size() <= 0) { //guard condition
				wait();//当某个线程试图去执行某个实例的wait方法时，这个线程必须获取该实例的锁定
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.error("requestQueue 被打断 {}", Thread.currentThread().getName());
		}

		return queue.removeFirst();
	}

	public synchronized void putRequest(Request request) {
		queue.addLast(request);
		notifyAll();
	}
}
