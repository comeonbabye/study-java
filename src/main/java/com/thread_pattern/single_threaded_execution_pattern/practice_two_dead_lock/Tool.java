package com.thread_pattern.single_threaded_execution_pattern.practice_two_dead_lock;

public class Tool {

	private final String name;

	public Tool(String name) {
		this.name = name;
	}

	public String toString() {
		return "[" + name + "]";
	}
}
