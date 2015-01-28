package com.design_pattern.singleton;

/**
 * 单例模式二
 * 延迟加载模式，第一次调用的时候才初始化，
 * 这种模式，比在方法名称上同步性能上有所提高，
 * 对于JVM而言，它执行的是一个个Java指令。在Java指令中创建对象和赋值操作是分开进行的，也就是说instance = new Singleton();语句是分两步执行的。
 * 但是JVM并不保证这两个操作的先后顺序，也就是说有可能JVM会为新的Singleton实例分配空间，然后直接赋值给instance成员，然后再去初始化这个Singleton实例。这样就使出错成为了可能
 * 这种情况再海量并发的情况下可能会发送错误，一般不会发送错误。
 * @author tony
 *
 */
public class Singleton2 {

	private static Singleton2 instance = null;

	private Singleton2() {
	}

	public static Singleton2 getInstance() {

		if(instance == null) {
			synchronized(Singleton2.class) {
				if(instance == null) {
					instance = new Singleton2();
				}
			}
		}
		return instance;
	}
}
