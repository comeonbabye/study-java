package com.thread_pattern.single_threaded_execution_pattern;

public class UserThread extends Thread {

	private final Gate gate;
	private final String myName;
	private final String myAddress;

	public UserThread(Gate gate, String myName, String myAddress) {
		this.gate = gate;
		this.myName = myName;
		this.myAddress = myAddress;
	}


	public void run() {
		System.out.println(myName + " BEGIN");
		while(true) {
			gate.pass(myName, myAddress);
		}
	}
}
