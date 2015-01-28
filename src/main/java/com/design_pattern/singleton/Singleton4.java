package com.design_pattern.singleton;

/**
 * 单例模式四
 * 在第一次调用的时候才初始化，但是多线程下有安全问题，
 * synchronized对方法加上同步，解决了安全问题，但是每次都调用锁，在海量的并发调用下，性能损耗严重
 * @author tony
 *
 */
public class Singleton4 {

	private static Singleton4 instance = null;

	private Singleton4() {
	}

	public static synchronized Singleton4 getInstance() {

		if(instance == null) {
			instance = new Singleton4();
		}
		return instance;
	}
}
