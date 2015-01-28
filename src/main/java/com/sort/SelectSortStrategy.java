package com.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.ArrayUtil;
import com.util.RandomUtil;

/**
 * 选择排序<br/>
 * <li>首先找出最大的元素，将其与a[n-1]位置交换</li>
 * <li>然后在余下的n-1个元素中寻找最大的元素，将其与a[n-2]位置交换</li>
 * <li>如此进行下去直至n个元素排序完毕</li>
 *
 * 比较次数多，交换次数少
 * @author tony
 */
public class SelectSortStrategy extends SortStrategy<int[]> {

	protected static final Logger log = LoggerFactory.getLogger(SelectSortStrategy.class);

	public static void main(String[] args) {

		SelectSortStrategy s = new SelectSortStrategy();

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

	public void sort(int[] a) {

		int i,j,k,t;

		for(i=0; i<a.length-1; i++) {
			for(k=i,j=i+1; j<a.length; j++) {
				if(a[k] > a[j]) {
					k = j;

				}
				count1++;
			}

			if(k != j) {
				t = a[k];
				a[k] = a[i];
				a[i] = t;
				count2+=3;
			}
		}

	}

}
