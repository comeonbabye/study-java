package com.study.test.tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestTomcatConcurrent {

	private static final String url = "http://192.168.1.55:8080/vs/api/comments_system/test.htm";
	private static final String url2 = "http://192.168.1.216:7086/test/api/comments_system/test.htm";
	
	/**
	 * 
	 * 功能: 测试服务器在大并发下的压力：
	 * 场景：150个并发线程像服务器发起请求，请求模拟实际业务处理，声明300K的内存，随机休眠1-5秒
	 * tomcat配置150个最大并发，100个队列请求
	 * 作者: tony.he
	 * 创建日期:2015-1-16
	 */
	public static void test() {
		
		ExecutorService es = Executors.newFixedThreadPool(150);
		
		for(int i=0; i<150; i++) {
			es.submit(new Runnable() {

				@Override
				public void run() {
					while(true) {
						
						if(Thread.interrupted()) {
							
							break;
						}
						try {
							Connection con = Jsoup.connect(url2).timeout(180000);
							Document doc = con.get();
							System.out.println(doc.text());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
	}
	
	/**
	 * 
	 * 功能: 测试服务器因为队列满，拒绝连接：
	 * 场景：100个并发线程像服务器发起请求，请求模拟实际业务处理，声明300K的内存，随机休眠1-5秒
	 * tomcat配置10个最大并发，10个队列请求
	 * 作者: tony.he
	 * 创建日期:2015-1-16
	 */
	public static void test1() {
		
		ExecutorService es = Executors.newFixedThreadPool(100);
		
		for(int i=0; i<100; i++) {
			es.submit(new Runnable() {

				@Override
				public void run() {
					while(true) {
						
						if(Thread.interrupted()) {
							break;
						}
						try {
							Connection con = Jsoup.connect(url).timeout(180000);
							Document doc = con.get();
							System.out.println(doc.text());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
		
	}
	
	/**
	 * 
	 * 功能: 测试服务器在大并发下的压力：
	 * 场景：150个并发线程像服务器发起请求，请求模拟实际业务处理，声明300K的内存，随机休眠1-5秒
	 * tomcat配置150个最大并发，100个队列请求
	 * 解决问题：socket关闭后端口不能立马重用，影响并发测试
	 * 
	 * 作者: tony.he
	 * 创建日期:2015-1-16
	 */
	public static void test2() {
		
		ExecutorService es = Executors.newFixedThreadPool(1000);
		
		for(int i=0; i<1000; i++) {
			es.submit(new Runnable() {

				@Override
				public void run() {
					while(true) {
						
						Socket socket = null;
						
						try {
							SocketAddress address = new InetSocketAddress("dev.com", 7086);
							socket = new Socket();
							socket.setKeepAlive(false); //对于长时间处理空闲状态的socket，底层连接是否需要自动把它关闭，2个小时候会尝试连接，如果11次都不成功，则关闭socket
							socket.setReuseAddress(true); //socket关闭后，端口可以重用
							socket.setTcpNoDelay(true); //数据立马发送出去
							socket.setSoTimeout(20000); //接收数据超时时间
							socket.setSendBufferSize(1000); //发送数据的缓冲区大小
							socket.setReceiveBufferSize(1000); //接收数据的缓冲区大小
							//socket.setSoLinger(true, 10000);  //关闭底层资源，最多等待10秒，等数据发送完毕，超过10秒强制关闭
							socket.setSoLinger(true, 1); //需要立即关闭，不然会报错误：Address already in use: connect
							socket.connect(address, 5000); //连接到目标地址，超时时间为5秒
							socket.setTrafficClass(0x04|0x10);//0x02:低成本 0x04:高可靠性 0x08:最高吞吐量 0x10:最小延迟 
							socket.setPerformancePreferences(2, 1, 3);//连接第二位，延迟第一位，带宽第三位
							
							StringBuffer sb = new StringBuffer("POST /test/api/comments_system/test.htm HTTP/1.1\r\n");
							sb.append("Accept: */*\r\n");
							sb.append("Accept-Encoding: gzip, deflate\r\n");
							sb.append("Accept-Language: zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3\r\n");
							sb.append("Host: dev.com\r\n");
							sb.append("User-Agent: Mozilla/5.0 (Windows NT 5.1; rv:34.0) Gecko/20100101 Firefox/34.0\r\n");
							sb.append("Connection: keep-alive\r\n\r\n");
							
							OutputStream os = socket.getOutputStream();
							os.write(sb.toString().getBytes());
							socket.shutdownOutput();
							
							InputStream is = socket.getInputStream();
							byte[] b = new byte[1024];
							int length = -1;
							sb = new StringBuffer();
							while((length = is.read(b)) != -1) {
								sb.append(new String(b, 0, length, "utf-8"));
								
							}
							socket.shutdownInput();
							System.out.println(sb.toString());
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							if(socket != null) {
								try {
									socket.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			});
		}
		
	}
	
	public static void test3() {
		
		Socket socket = null;
		
		try {
			SocketAddress address = new InetSocketAddress("dev.com", 7086);
			socket = new Socket();
			socket.setKeepAlive(false); //对于长时间处理空闲状态的socket，底层连接是否需要自动把它关闭，2个小时候会尝试连接，如果11次都不成功，则关闭socket
			socket.setReuseAddress(true); //socket关闭后，端口可以重用
			socket.setTcpNoDelay(true); //数据立马发送出去
			socket.setSoTimeout(20000); //接收数据超时时间
			socket.setSendBufferSize(1000); //发送数据的缓冲区大小
			socket.setReceiveBufferSize(1000); //接收数据的缓冲区大小
			//socket.setSoLinger(true, 10000);  //关闭底层资源，最多等待10秒，等数据发送完毕，超过10秒强制关闭
			socket.setSoLinger(true, 1); //需要立即关闭，不然会报错误：Address already in use: connect
			socket.connect(address, 5000); //连接到目标地址，超时时间为5秒
			socket.setTrafficClass(0x04|0x10);//0x02:低成本 0x04:高可靠性 0x08:最高吞吐量 0x10:最小延迟 
			socket.setPerformancePreferences(2, 1, 3);//连接第二位，延迟第一位，带宽第三位
			
			StringBuffer sb = new StringBuffer("POST /test/api/comments_system/test.htm HTTP/1.1\r\n");
			sb.append("Accept: */*\r\n");
			sb.append("Accept-Encoding: gzip, deflate\r\n");
			sb.append("Accept-Language: zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3\r\n");
			sb.append("Host: dev.com\r\n");
			sb.append("User-Agent: Mozilla/5.0 (Windows NT 5.1; rv:34.0) Gecko/20100101 Firefox/34.0\r\n");
			sb.append("Connection: keep-alive\r\n\r\n");
			
			OutputStream os = socket.getOutputStream();
			os.write(sb.toString().getBytes());
			socket.shutdownOutput();
			
			InputStream is = socket.getInputStream();
			byte[] b = new byte[1024];
			int length = -1;
			sb = new StringBuffer();
			while((length = is.read(b)) != -1) {
				sb.append(new String(b, 0, length, "utf-8"));
				
			}
			socket.shutdownInput();
			System.out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		
		
		test2();
	}

}

