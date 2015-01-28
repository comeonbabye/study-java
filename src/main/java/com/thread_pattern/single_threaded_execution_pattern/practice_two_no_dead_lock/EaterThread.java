package com.thread_pattern.single_threaded_execution_pattern.practice_two_no_dead_lock;

import java.util.concurrent.locks.Lock;

public class EaterThread extends Thread {

	private String name;
	private final Tool leftHand;
	private final Tool rightHand;

	private final Lock lock;

	public EaterThread(String name, Tool leftHand, Tool rightHand, Lock lock) {
		this.name = name;
		this.leftHand = leftHand;
		this.rightHand = rightHand;
		this.lock = lock;
	}

	public void run() {
		while(true) {
			eat();
		}
	}

	public void eat() {

		lock.lock();
		try {

			System.out.println(name + " takes up " + leftHand + "(left).");
			System.out.println(name + " takes up " + rightHand + "(right).");

			System.out.println(name + " is eating now, yam yam!");
			System.out.println(name + " puts down " + rightHand + "(right).");
			System.out.println(name + " puts down " + leftHand + "(left).");
		} finally {
			lock.unlock();
		}

	}
}
