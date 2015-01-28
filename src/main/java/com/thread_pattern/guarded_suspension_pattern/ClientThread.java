package com.thread_pattern.guarded_suspension_pattern;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发送请求的线程
 * @author tony
 *
 */
public class ClientThread extends Thread {

	protected static final Logger log = LoggerFactory.getLogger(ClientThread.class);


	private Random random;
	private RequestQueue requestQueue;
	public ClientThread(RequestQueue requestQueue, String name, long seed) {
		super(name);
		this.requestQueue = requestQueue;
		this.random = new Random(seed);
	}

	public void run() {
		for(int i=0; i<10000; i++) {

			Request request = new Request("No." + i);
			log.info("{} requests {}", Thread.currentThread().getName(), request);
			requestQueue.putRequest(request);
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
