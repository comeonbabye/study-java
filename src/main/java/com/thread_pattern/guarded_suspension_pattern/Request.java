package com.thread_pattern.guarded_suspension_pattern;

/**
 * 用来表示请求的类，这里只存一个名字
 * 这个是一个线程安全的类，immutable，不可修改
 * @author tony
 *
 */
public class Request {

	private final String name;

	public Request(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return "[ Request " + name + " ]";
	}
}
