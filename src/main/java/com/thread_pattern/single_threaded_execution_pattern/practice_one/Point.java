package com.thread_pattern.single_threaded_execution_pattern.practice_one;

/**
 *子类不能继承final关键词修饰的类，
 *而且子类继承的父类，必须有显示声明无参的构造函数
 * @author tony
 *
 */
public final class Point {
	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public synchronized void move(int dx, int dy) {
		x += dx;
		y += dy;
	}
}
