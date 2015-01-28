package com.sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.ArrayUtil;
import com.util.RandomUtil;


/**
 *冒泡排序：时间复杂度O(n2)<br/>
 * <li>比较相邻的元素。如果第一个比第二个大，就交换他们两个。</li>
 * <li>对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。</li>
 * <li>针对所有的元素重复以上的步骤，除了最后一个。</li>
 * <li>持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。</li>
 *
 * 比较次数多，交换次数也多
 * @author tony
 *
 */
public class BubblingSortStrategy extends SortStrategy<int[]> {

	protected static final Logger log = LoggerFactory.getLogger(BubblingSortStrategy.class);
	
	public static void main(String[] args) {
		
		BubblingSortStrategy s = new BubblingSortStrategy();

		int[] a = RandomUtil.getRandomInt(20);
		
		s.printBefore(a);
		s.sort(a);
		s.printAfter(a);
	}
	
	private int count1 = 0, count2 = 0;

	@Override
	public void printBefore(int[] t) {
		log.info("==============冒泡排序==============");
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

		int temp;
		for(int i=0; i<a.length - 1; i++) {
			for(int j= i + 1; j<a.length; j++) {
				if(a[i] > a[j]) {
					temp = a[j];
					a[j] = a[i];
					a[i] = temp;
					count2+=3;
				}
				count1++;
			}
		}
	}

}
