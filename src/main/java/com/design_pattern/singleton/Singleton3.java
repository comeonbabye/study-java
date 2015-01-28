package com.design_pattern.singleton;

/**
 * 单例模式三
 * 通过内部类在第一次调用的时候加载SingletonContainer静态类，静态初始化instance，
 * 避免线程之间的问题，这样试下懒加载模式，避免一开始就初始化调用构造函数
 * @author tony
 *
 */
public class Singleton3 {

	private Singleton3() {

	}

	public static Singleton3 getInstance() {

		return SingletonContainer.instance;
	}

	private static class SingletonContainer {

		private static Singleton3 instance = new Singleton3();
	}
}
