package com.cache.memcache;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cache.LRU.LRUCache;
import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class MemcachedJavaClientTest {

	protected static final Logger log = LoggerFactory.getLogger(LRUCache.class);
	
	private static final String IP = "192.168.3.211";
	private static final int PORT = 11211;
	
	/**
	 * 
	 * 功能: 单线程大概耗时14281毫秒、13266毫秒
	 * 作者: tony.he
	 * 创建日期:2015-1-30
	 */
	public static void testSingleThreadSetAndGet() {
		/**  
		 * 初始化SockIOPool，管理memcached的连接池  
		 */
		String[] servers = { IP + ":" + PORT };

		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers);
		pool.setFailover(true);
		pool.setInitConn(1);
		pool.setMinConn(1);
		pool.setMaxConn(1);
		pool.setMaintSleep(30);
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setAliveCheck(true);
		pool.initialize();

		long s = System.currentTimeMillis();
		
		MemCachedClient memCachedClient = new MemCachedClient();
		for (int i = 0; i < 1000; i++) {

			boolean success = memCachedClient.set("" + i, "Hello!");
			String result = (String) memCachedClient.get("" + i);
			System.out.println(String.format("set( %d ): %s", i, success));
			System.out.println(String.format("get( %d ): %s", i, result));

		}
		
		log.info("single thread elapsed time:{}", (System.currentTimeMillis() - s));
	}
	
 
	/**
	 * 
	 * 功能: 10个线程运行多次耗时4734毫秒、2516毫秒、2219毫秒、2828毫秒、1812毫秒
	 * 		去掉打印耗时：2016毫秒、4438毫秒、4625毫秒、1922毫秒、3031毫秒
	 * 		去掉get和打印耗时：1515毫秒、1109毫秒、1516毫秒
	 * 启用线程池开启多个线程会好很多
	 * 作者: tony.he
	 * 创建日期:2015-1-30
	 * 修改者: mender
	 * 修改日期: modifydate
	 */
	public static void testMutilThreadSetAndGet() {
		
		/**  
		 * 初始化SockIOPool，管理memcached的连接池  
		 */
		String[] servers = { IP + ":" + PORT };

		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers);
		pool.setFailover(true);
		pool.setInitConn(10);
		pool.setMinConn(10);
		pool.setMaxConn(10);
		pool.setMaintSleep(30);
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setAliveCheck(true);
		pool.initialize();
		
		final AtomicInteger count = new AtomicInteger(0);
		
		ExecutorService es = Executors.newFixedThreadPool(10);
		
		long s = System.currentTimeMillis();
		
		for(int j=0; j<10; j++) {
			es.submit(new Runnable(){

				@Override
				public void run() {
					
					MemCachedClient memCachedClient = new MemCachedClient();
					
					for (int i = 0; i < 100; i++) {

						try {
							boolean success = memCachedClient.set("" + i, "Hello!");
							String result = (String) memCachedClient.get("" + i);
							/*System.out.println(String.format("set( %d ): %s", i, success));
							System.out.println(String.format("get( %d ): %s", i, result));*/
						} catch (Exception e) {
							e.printStackTrace();
							log.error("error:{}", e);
						} finally {
							count.incrementAndGet();
						}
					}
				}
			});
		}
		
		try {
			while(count.get() < 1000) {
				Thread.sleep(100L);
			}
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		} finally {
			try{
				es.shutdownNow();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				pool.shutDown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		log.info("mutil thread elapsed time:{}", (System.currentTimeMillis() - s));
		
	}
	
	/**
	 * 
	 * 功能: 单线程的NIO反而比BIO更加耗时：18594毫秒、22110毫秒
	 * 作者: tony.he
	 * 创建日期:2015-1-30
	 */
	public static void testSingleThreadSpyMemcache() {
		
		long s = System.currentTimeMillis();
		MemcachedClient mc = null; 
		try {

			mc = new MemcachedClient(new InetSocketAddress(IP, PORT));
			Future<Boolean> future = null;
			
			
			for (int i = 0; i < 1000; i++) {
				future = mc.set("" + i, 1000, "Hello!");
				if (future.get().booleanValue() == true) {
					Object result = mc.get("" + i);
					System.out.println(String.format("set( %d ): %s", i, true));
					System.out.println(String.format("get( %d ): %s", i, result));
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if(mc != null) {
				try {
					mc.shutdown();
				} catch (Exception e) { 
					e.printStackTrace();
				}
			}
		}
		log.info("single thread spy test elapsed time:{}", (System.currentTimeMillis() - s));
	}
	
	/**
	 * 
	 * 功能: 耗时7484毫秒、7390毫秒，去掉打印6625毫秒、(会有超时情况，不稳定)、7734毫秒、去掉get耗时：829毫秒、297毫秒、328毫秒
	 * 作者: tony.he
	 * 创建日期:2015-1-30
	 */
	public static void testMutilThreadSpyMemcache() {
		
		long s = System.currentTimeMillis();
		
		try {

			final MemcachedClient mc = new MemcachedClient(new InetSocketAddress(IP, PORT));
			
			Map<String, Future<Boolean>> map = new HashMap<String, Future<Boolean>>();
			Future<Boolean> future = null;
			for (int i = 0; i < 1000; i++) {
				future = mc.set("" + i, 1000, "Hello!");
				map.put("" + i, future);
			}
			 
			Set<String> keys = new HashSet<String>();
			while(!map.isEmpty()) {
				
				for(String key : map.keySet()) {
					if (map.get(key).get().booleanValue() == true) {
						keys.add(key);
						/*Object result = mc.get(key);
						System.out.println(String.format("set( %s ): %s", key, true));
						System.out.println(String.format("get( %s ): %s", key, result));*/
					}
				}
				
				for(String key : keys) {
					map.remove(key);
				}
				
				keys.clear();
				
				Thread.sleep(10);
			}
			
			if(mc != null) {
				try {
					mc.shutdown();
				} catch (Exception e) { 
					e.printStackTrace();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}  
		log.info("mutil thread spy test elapsed time:{}", (System.currentTimeMillis() - s));
	}

	public static void main(String[] args) {
		
		testMutilThreadSetAndGet();

	}

}
