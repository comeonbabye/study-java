package com.cache.consistent;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 算法太简单，单一，测试效果不好，分布不均匀，需要根据ip来hash，这里的hash方法不好，通过索引序号，不利于机器增加和删除
 * @author tony
 *
 */
public class ConsistencyHashDemo {

	private TreeMap<Long, Object> nodes = null;

	//真实服务器节点信息  
	private List<Object> shards = new ArrayList<Object>();

	//设置虚拟节点数目  
	private int VIRTUAL_NUM = 4;

	/** 
	 * 初始化一致环 
	 */
	public void init() {
		shards.add("192.168.0.0-服务器0");
		shards.add("192.168.0.1-服务器1");
		shards.add("192.168.0.2-服务器2");
		shards.add("192.168.0.3-服务器3");
		shards.add("192.168.0.4-服务器4");

		nodes = new TreeMap<Long, Object>();
		for (int i = 0; i < shards.size(); i++) {
			Object shardInfo = shards.get(i);
			for (int j = 0; j < VIRTUAL_NUM; j++) {
				nodes.put(hash(computeMd5("SHARD-" + i + "-NODE-" + j), j),
						shardInfo);
			}
		}
	}

	/** 
	 * 根据2^32把节点分布到圆环上面。 
	 * @param digest 
	 * @param nTime 
	 * @return 
	 */
	public long hash(byte[] digest, int nTime) {
		long rv = ((long) (digest[3 + nTime * 4] & 0xFF) << 24)
				| ((long) (digest[2 + nTime * 4] & 0xFF) << 16)
				| ((long) (digest[1 + nTime * 4] & 0xFF) << 8)
				| (digest[0 + nTime * 4] & 0xFF);

		return rv & 0xffffffffL; /* Truncate to 32-bits */
	}

	/** 
	 * Get the md5 of the given key. 
	 * 计算MD5值 
	 */
	public byte[] computeMd5(String k) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 not supported", e);
		}
		md5.reset();
		byte[] keyBytes = null;
		try {
			keyBytes = k.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unknown string :" + k, e);
		}

		md5.update(keyBytes);
		return md5.digest();
	}

	/** 
	 * 根据key的hash值取得服务器节点信息 
	 * @param hash 
	 * @return 
	 */
	public Object getShardInfo(long hash) {
		Long key = hash;
		SortedMap<Long, Object> tailMap = nodes.tailMap(key);
		if (tailMap.isEmpty()) {
			key = nodes.firstKey();
		} else {
			key = tailMap.firstKey();
		}
		return nodes.get(key);
	}

	/** 
	 * 打印圆环节点数据 
	 */
	public void printMap() {
		System.out.println(nodes);
	}

	public static void main(String[] args) {

		Map<String, Integer> count = new HashMap<String, Integer>();

		Random ran = new Random();
		ConsistencyHashDemo hash = new ConsistencyHashDemo();
		hash.init();
		hash.printMap();
		//循环50次，是为了取50个数来测试效果，当然也可以用其他任何的数据来测试  
		for (int i = 0; i < 50000; i++) {
			String obj = (String)hash.getShardInfo(hash.hash(hash.computeMd5(String.valueOf(i)), ran.nextInt(hash.VIRTUAL_NUM)));
			//System.out.println(obj);
			Integer c = count.get(obj);
			c = (c == null ? 1 : c + 1);
			count.put(obj, c);
		}
		
		System.out.println(count);

	}

}
