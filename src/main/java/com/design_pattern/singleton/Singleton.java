package com.design_pattern.singleton;

/**
 * 单例模式一
 * 这种情况比较安全和简单，但是会在一开始就调用构造函数初始化
 * @author tony
 *
 */
public class Singleton {

	private static final Singleton instance = new Singleton();

	private Singleton() {
	}

	public static Singleton getInstance() {
		return instance;
	}
}
