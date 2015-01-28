package com.thread_pattern.single_threaded_execution_pattern.practice_two_no_dead_lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockMain {

	public static void main(String[] args) {

		System.out.println("Testing EaterThread, hit CRTL+C to exit.");

		Tool spoon = new Tool("Spoon");
		Tool fork = new Tool("Fork");
		Lock lock = new ReentrantLock();//只对一个对象加锁就不会出现死锁了
		new EaterThread("Alice", spoon, fork, lock).start();
		new EaterThread("Bobby", fork, spoon, lock).start();

	}

}
