package com.thread_pattern.guarded_suspension_pattern;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 接收请求的线程
 * @author tony
 *
 */
public class ServerThread extends Thread {

	protected static final Logger log = LoggerFactory.getLogger(ServerThread.class);


	private Random random;
	private RequestQueue requestQueue;
	public ServerThread(RequestQueue requestQueue, String name, long seed) {
		super(name);
		this.requestQueue = requestQueue;
		this.random = new Random(seed);
	}

	public void run() {
		for(int i=0; i<10000; i++) {
			Request request = requestQueue.getRequest();
			log.info("{} handles {}", Thread.currentThread().getName(), request);
			try {
				Thread.sleep(random.nextInt(1000));
			} catch(InterruptedException e) {
				e.printStackTrace();
				log.error("{} 被打断, break out.", Thread.currentThread().getName());
				break;
			}
		}
	}
}
