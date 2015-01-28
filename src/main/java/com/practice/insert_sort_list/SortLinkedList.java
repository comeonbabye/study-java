package com.practice.insert_sort_list;

class ListNode {
	public int val;
	public ListNode next;

	ListNode(int x) {
		this.val = x;
		this.next = null;
	}
}

/**
 * 插入排序链表
 * @see http://www.programcreek.com/2012/11/leetcode-solution-sort-a-linked-list-using-insertion-sort-in-java/
 * @author tony
 *
 */
public class SortLinkedList {

	public static ListNode insertionSortList(ListNode head) {

		if (head == null || head.next == null)
			return head;

		//存储首节点
		ListNode newHead = new ListNode(head.val);
		ListNode pointer = head.next;

		// loop through each element in the list
		while (pointer != null) { 
			// insert this element to the new list

			ListNode innerPointer = newHead;
			ListNode next = pointer.next;

			if (pointer.val <= newHead.val) { //如果比head节点还小，那么作为新的head节点
				ListNode oldHead = newHead;
				newHead = pointer;
				newHead.next = oldHead;
			} else {
				//如果比首节点打，那么继续变量后面的节点，
				while (innerPointer.next != null) {
					if (pointer.val > innerPointer.val && pointer.val <= innerPointer.next.val) {//如果需要插入到前面已经排序的链表中间，则插入
						ListNode oldNext = innerPointer.next;
						innerPointer.next = pointer;
						pointer.next = oldNext;
					}

					innerPointer = innerPointer.next;
				}

				if (innerPointer.next == null && pointer.val > innerPointer.val) { //如果遍历完了，比前面已经排序的链表节点都大，则插入到最后
					innerPointer.next = pointer;
					pointer.next = null;
				}
			}

			// finally
			pointer = next; //继续变量后面的节点
		}

		return newHead;
	}

	public static void main(String[] args) {
		
		ListNode n1 = new ListNode(2);
		ListNode n2 = new ListNode(3);
		ListNode n3 = new ListNode(4);

		ListNode n4 = new ListNode(3);
		ListNode n5 = new ListNode(4);
		ListNode n6 = new ListNode(5);

		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		n5.next = n6;

		//插入排序
		n1 = insertionSortList(n1);

		//打印排序好的链表
		printList(n1);

	}

	public static void printList(ListNode x) {
		if (x != null) {
			System.out.print(x.val + " ");
			while (x.next != null) {
				System.out.print(x.next.val + " ");
				x = x.next;
			}
			System.out.println();
		}

	}
}
