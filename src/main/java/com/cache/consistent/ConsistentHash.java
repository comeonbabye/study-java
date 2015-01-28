package com.cache.consistent;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * 创建日期:2015-1-28
 * Title: 节点越少，增加的虚拟节点越多，才能达到一致性
 * 
 * @author tony.he
 * @version 1.0
 * @param <T>
 */
public class ConsistentHash<T> {
	private final HashFunction hashFunction;
	private final int numberOfReplicas;
	private final SortedMap<Integer, T> circle = new TreeMap<Integer, T>();

	public ConsistentHash(HashFunction hashFunction, int numberOfReplicas, Collection<T> nodes) {
		this.hashFunction = hashFunction;
		this.numberOfReplicas = numberOfReplicas;

		for (T node : nodes) {
			add(node);
		}
	}

	public void add(T node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.put(hashFunction.hash(node.toString() + i), node);
		}
	}

	public void remove(T node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.remove(hashFunction.hash(node.toString() + i));
		}
	}

	public T get(Object key) {
		if (circle.isEmpty()) {
			return null;
		}
		int hash = hashFunction.hash(key);
		// System.out.println("hash---: " + hash);
		if (!circle.containsKey(hash)) {
			SortedMap<Integer, T> tailMap = circle.tailMap(hash);
			hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
		}
		// System.out.println("hash---: " + hash);
		return circle.get(hash);
	}

	static class HashFunction {
		int hash(Object key) {
			// md5加密后，hashcode
			// return Md5Encrypt.md5(key.toString()).hashCode();
			return DigestUtils.md5Hex(key.toString()).hashCode();
		}

		/**
		 * Get the md5 of the given key. 计算MD5值
		 */
		byte[] computeMD5(String k) {
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
	}

	public static void main(String[] args) {
		HashSet<String> set = new HashSet<String>();
		set.add("A");
		set.add("B");
		set.add("C");
		set.add("D");

		Map<String, Integer> map = new HashMap<String, Integer>();

		ConsistentHash<String> consistentHash = new ConsistentHash<String>(new HashFunction(), 500, set);

		int count = 200000;

		for (int i = 0; i < count; i++) {
			String key = consistentHash.get(i);
			if (map.containsKey(key)) {
				map.put(consistentHash.get(i), map.get(key) + 1);
			} else {
				map.put(consistentHash.get(i), 1);
			}
			// System.out.println(key);
		}

		showServer(map);

		map.clear();
		consistentHash.remove("A");

		System.out.println("------- remove A");

		for (int i = 0; i < count; i++) {
			String key = consistentHash.get(i);
			if (map.containsKey(key)) {
				map.put(consistentHash.get(i), map.get(key) + 1);
			} else {
				map.put(consistentHash.get(i), 1);
			} 
			//System.out.println(key);
		}

		showServer(map);

		map.clear();
		consistentHash.add("E");
		System.out.println("------- add E");

		for (int i = 0; i < count; i++) {
			String key = consistentHash.get(i);
			if (map.containsKey(key)) {
				map.put(consistentHash.get(i), map.get(key) + 1);
			} else {
				map.put(consistentHash.get(i), 1);
			} 
			//System.out.println(key);
		}
		showServer(map);
		
		map.clear();
		consistentHash.add("F");
		System.out.println("------- add F服务器业务量加倍");
		count = count * 2;
		for (int i = 0; i < count; i++) {
			String key = consistentHash.get(i);
			if (map.containsKey(key)) {
				map.put(consistentHash.get(i), map.get(key) + 1);
			} else {
				map.put(consistentHash.get(i), 1);
			}
			// System.out.println(key);
		}

		showServer(map);

	}

	public static void showServer(Map<String, Integer> map) {
		for (Entry<String, Integer> m : map.entrySet()) {
			System.out.println("服务器 " + m.getKey() + "----" + m.getValue() + "个");
		}
	}
}
