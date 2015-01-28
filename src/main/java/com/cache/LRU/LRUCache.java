package com.cache.LRU;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * LRU算法缓存
 * 1. 通过Map快速定位缓存是否存在
 * 2. 每次命中缓存后会把节点从链表的中间删除，然后添加到head中，没有命中会直接返回null
 * 3. 插入缓存的时候如果存在，则更新值，然后从链表的中间移除，然后添加到head中，
 * 		如果不存在，则判断是否达到了容量，
 * 			如果达到了容量，则移除end节点，然后添加到head，然后添加到缓存map中
 * 			如果没有达到容量，则直接添加到head，然后添加到缓存map中
 * @author tony
 *
 */
public class LRUCache {

	protected static final Logger log = LoggerFactory.getLogger(LRUCache.class);
	
	private HashMap<Object, DoubleLinkedListNode> map = new HashMap<Object, DoubleLinkedListNode>();
	private DoubleLinkedListNode head;
	private DoubleLinkedListNode end;
	private int capacity;
	private int len;

	public LRUCache(int capacity) {
		this.capacity = capacity;
		len = 0;
	}

	public synchronized Object get(Object key) {
		if (map.containsKey(key)) {
			DoubleLinkedListNode latest = map.get(key);
			removeNode(latest);
			setHead(latest);
			return latest.val;
		} else {
			return null;
		}
	}

	public synchronized void removeNode(DoubleLinkedListNode node) {
		DoubleLinkedListNode cur = node;
		DoubleLinkedListNode pre = cur.pre;
		DoubleLinkedListNode post = cur.next;

		if (pre != null) {
			pre.next = post;
		} else {
			head = post;
		}

		if (post != null) {
			post.pre = pre;
		} else {
			end = pre;
		}
	}

	private void setHead(DoubleLinkedListNode node) {
		node.next = head;
		node.pre = null;
		if (head != null) {
			head.pre = node;
		}

		head = node;
		if (end == null) {
			end = node;
		}
	}

	public synchronized void set(Object key, Object value) {
		if (map.containsKey(key)) {
			DoubleLinkedListNode oldNode = map.get(key);
			oldNode.val = value;
			removeNode(oldNode);
			setHead(oldNode);
		} else {
			DoubleLinkedListNode newNode = new DoubleLinkedListNode(key, value);
			if (len < capacity) {
				setHead(newNode);
				map.put(key, newNode);
				len++;
			} else {
				map.remove(end.key);
				end = end.pre;
				if (end != null) {
					end.next = null;
				}

				setHead(newNode);
				map.put(key, newNode);
			}
		}
	}
	
	public synchronized void display() {
		
		if(head == null) return;
		
		DoubleLinkedListNode node = head;
		
		log.info("===============cache infor==================");
		
		while(node != null) {
			
			log.info("key:{}, value:{}", node.key, node.val);
			
			node = node.next;
			
		}
		
	}
	
	public static void main(String[] args) {
		
		LRUCache cache = new LRUCache(3);
		
		String key = "one";
		String value = "one";
		
		cache.set(key, value);
		
		log.info("get key:{}, value:{}", key, cache.get(key));
		
		key = "two";
		value = "two";
		cache.set(key, value);
		
		key = "three";
		value = "three";
		cache.set(key, value);
		
		key = "four";
		value = "four";
		cache.set(key, value);
		
		
		key = "five";
		log.info("get key:{}, value:{}", key, cache.get(key));
		
		cache.display();
	}

}



class DoubleLinkedListNode {
	
	public Object val;
	public Object key;
	public DoubleLinkedListNode pre;
	public DoubleLinkedListNode next;

	public DoubleLinkedListNode(Object key, Object value) {
		this.key = key;
		this.val = value;
	}
}