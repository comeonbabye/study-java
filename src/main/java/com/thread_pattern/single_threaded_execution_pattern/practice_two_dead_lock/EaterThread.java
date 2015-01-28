package com.thread_pattern.single_threaded_execution_pattern.practice_two_dead_lock;

public class EaterThread extends Thread {

	private String name;
	private final Tool leftHand;
	private final Tool rightHand;

	public EaterThread(String name, Tool leftHand, Tool rightHand) {
		this.name = name;
		this.leftHand = leftHand;
		this.rightHand = rightHand;
	}

	public void run() {
		while(true) {
			eat();
		}
	}

	public void eat() {
		synchronized(leftHand) {
			System.out.println(name + " takes up " + leftHand + "(left).");
			synchronized(rightHand) {
				System.out.println(name + " takes up " + rightHand + "(right).");

				System.out.println(name + " is eating now, yam yam!");
				System.out.println(name + " puts down " + rightHand + "(right).");
				System.out.println(name + " puts down " + leftHand + "(left).");
			}
		}
	}
}
