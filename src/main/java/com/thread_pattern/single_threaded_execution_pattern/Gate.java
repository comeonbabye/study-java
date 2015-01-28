package com.thread_pattern.single_threaded_execution_pattern;


/**
 * SharedResource(共享资源)参与者, 只有唯一的线程可以在同一时刻访问
 * @author tony
 *
 */
public class Gate {

	private int counter = 0;
	private String name = "Nobody";
	private String address = "nowhere";

	/**
	 * unsafe method 需要同步
	 * @param name
	 * @param address
	 */
	public synchronized void pass(String name, String address) {
		this.counter++;
		this.name = name;
		this.address = address;
		check();
	}

	/**
	 * safe method 在unsafe method 里面，不需同步
	 *
	 */
	private void check() {
		if(name.charAt(0) != address.charAt(0)) {
			System.out.println("*****BROKEN*****" + toString());
		}
	}

	/**
	 *公共方法 unsafe method 需要同步
	 */
	public synchronized String toString() {
		return "No." + counter + ": " + name + ", " + address;
	}
}
