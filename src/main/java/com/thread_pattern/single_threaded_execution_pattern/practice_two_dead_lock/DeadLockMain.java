package com.thread_pattern.single_threaded_execution_pattern.practice_two_dead_lock;

public class DeadLockMain {

	public static void main(String[] args) {

		System.out.println("Testing EaterThread, hit CRTL+C to exit.");

		Tool spoon = new Tool("Spoon");
		Tool fork = new Tool("Fork");
		new EaterThread("Alice", spoon, fork).start();
		new EaterThread("Bobby", fork, spoon).start();

	}

}
