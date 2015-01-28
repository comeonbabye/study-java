package com.sort;

import com.util.RandomUtil;

public class SortMain {

	/**
	 * 功能: 各种算法调用
	 * 作者: tony.he
	 * 创建日期:2015-1-6
	 * @param args
	 */
	public static void main(String[] args) {
		
		int size = 20000, seed = 100000;
		
		int[] a = null;
		SortContext<int[]> sortContext = null;
		
		a = RandomUtil.getRandomInt(size, seed);
		sortContext = new SortContext<int[]>(new BubblingSortStrategy());  
		sortContext.sort(a);
		
		a = RandomUtil.getRandomInt(size, seed);
		sortContext = new SortContext<int[]>(new SelectSortStrategy());  
		sortContext.sort(a);
		
		a = RandomUtil.getRandomInt(size, seed);
		sortContext = new SortContext<int[]>(new InsertSortStrategy());  
		sortContext.sort(a);
		
		a = RandomUtil.getRandomInt(size, seed);
		sortContext = new SortContext<int[]>(new QuickSortStrategy());  
		sortContext.sort(a);

	}

}
