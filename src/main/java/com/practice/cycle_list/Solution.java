package com.practice.cycle_list;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 给定一个链表，判断是否有闭环
 * 
 * @author tony
 *
 */
public class Solution {

	protected static final Logger log = LoggerFactory.getLogger(Solution.class);
	
	/**
	 * 天真的方法:只判断是否和head节点有闭环
	 * @param head
	 * @return
	 */
	public boolean hasCycle(ListNode head) {
		
        ListNode p = head;
 
        if(head == null) //如果节点为空
        	
            return false;
 
        if(p.next == null) //如果只有一个节点
            
        	return false;
 
        while(p.next != null){
        	
            if(head == p.next){
                return true;
            }
            
            p = p.next;
        }
 
        return false;
    }
	
	
	/**
	 * Use fast and low pointer. The advantage about fast/slow pointers is that when a circle is located, the fast one will catch the slow one for sure.
	 * 用一个快的指针和一个慢的指针，如果有闭环的话，那么快的指针一定能跑过慢的指针
	 * @param head
	 * @return
	 */
	public boolean hasCycle2(ListNode head) {
		
        ListNode fast = head;
        ListNode slow = head;
 
        if(head == null)
            return false;
 
        if(head.next == null)
            return false;
 
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
 
            if(slow == fast)
                return true;
        }
 
        return false;
    }
	
	public void testOne() {
		ListNode current = null;
		ListNode head = new ListNode("1");
		ListNode node = new ListNode("2");
		
		head.next = node;
		current = node;
		node = new ListNode("3");
		current.next = node;
		node.next = head;
		
		log.info("Naive Approach, has cycyle : {}", hasCycle(head));
	}
	
	public void testTwo() {
		ListNode current = null;
		ListNode head = new ListNode("1");
		ListNode node = new ListNode("2");
		
		head.next = node;
		current = node;
		node = new ListNode("3");
		current.next = node;
		node.next = current;
		
		log.info("Naive Approach, has cycyle : {}", hasCycle(head));
	}
	
	public void testThree() {
		ListNode current = null;
		ListNode head = new ListNode("1");
		ListNode node = new ListNode("2");
		
		head.next = node;
		current = node;
		node = new ListNode("3");
		current.next = node;
		node.next = current;
		
		log.info("Accepted Approach, has cycyle : {}", hasCycle2(head));
	}
	
	public static void main(String[] args) {
		
		
		Solution s = new Solution();
		s.testOne(); //只能判断是否和第一个节点成闭环
		//s.testTwo(); //不能判断其他节点是否成为闭环，而且会一直循环下去
		s.testThree();
	}
}
