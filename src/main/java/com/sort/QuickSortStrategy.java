package com.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.ArrayUtil;
import com.util.RandomUtil;

public class QuickSortStrategy extends SortStrategy<int[]> {

	protected static final Logger log = LoggerFactory.getLogger(QuickSortStrategy.class);

	public static void main(String[] args) {

		QuickSortStrategy s = new QuickSortStrategy();

		int[] a = RandomUtil.getRandomInt(20);
		
		s.printBefore(a);
		s.sort(a);
		s.printAfter(a);
	}
	
	private int count1 = 0, count2 = 0;

	@Override
	public void printBefore(int[] t) {
		log.info("==============快速排序==============");
		log.info("排序前...........");
		log.info("{}", ArrayUtil.join(t, "\t"));
	}
	
	@Override
	public void printAfter(int[] t) {
		
		log.info("比较次数：{}", count1);
		log.info("交换次数：{}", count2);
		
		log.info("排序后...........");
		log.info("{}", ArrayUtil.join(t, "\t"));
	}
	
	@Override
	public void sort(int[] a) {
		quickSort(a, 0, a.length - 1);
	}

	private void quickSort(int[] a, int l, int r) {

		if(l >= r) return;

		int i = l;	//从左到右的游标
		int j = r + 1;//从右到左的游标
		int t, temp = a[l];//以a[l]为支点

		while(true) {
			while(a[++i] < temp && i < r) {
				count1++; //从左边找>=temp的元素
			}
			while(a[--j] > temp && j > l) {
				count1++;//从右边找<=temp的元素
			}

			if(i >= j) break; //如果未找到交换对，退出循环

			t = a[i];
			a[i] = a[j];
			a[j] = t;
			count2+=3;
		}

		//将支点a[l]与a[j]交换
		a[l] = a[j];
		a[j] = temp;
		count2+=2;

		quickSort(a, l, j - 1); //左段快速排序
		quickSort(a, j + 1, r); //右段快速排序

	}
}
