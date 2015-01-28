package com.thread_pattern.guarded_suspension_pattern;

public class Main {

	public static void main(String[] args) {

		RequestQueue requestQueue = new RequestQueue();

		ClientThread ct = new ClientThread(requestQueue, "Alice", 3141592L);
		ServerThread st = new ServerThread(requestQueue, "Bobby", 6535897L);

		ct.start();
		st.start();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ct.interrupt();
		st.interrupt();
	}

}
