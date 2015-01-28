package com.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sort.QuickSortStrategy;
import com.sort.SortContext;
import com.util.ArrayUtil;
import com.util.RandomUtil;

/**
 *
 * 二分查找
 * 随着数据量呈指数上升，比较次数不会有太大上升，从而查询数据非常之快
 * 通过在while循环里面查询，比递归查询性能高，因为递归会有额外的方法调用开销，这种情况下不建议使用递归
 * @author tony.he
 * @version 1.0
 */
public class BinarySearch {

	protected static final Logger log = LoggerFactory.getLogger(BinarySearch.class);


	public static void main(String[] args) {

		int size = 500000, seed = 50000;

		int[] a = null;
		SortContext<int[]> sortContext = null;

		a = RandomUtil.getRandomIntNoDuplicate(size, seed);
		sortContext = new SortContext<int[]>(new QuickSortStrategy());
		sortContext.sort(a);


		//printBefore(a);

		long t = System.currentTimeMillis();
		int index = binarySearch(a, 122);
		log.info("sort elapsed time:{}, compare time:{}", (System.currentTimeMillis() - t), count1);

		if(index > 0) {
			log.info("find in index:{}, value:{}", index, a[index]);
		} else {
			log.info("not found");
		}
	}

	public static void printBefore(int[] t) {
		log.info("排序前...........");
		log.info("{}", ArrayUtil.join(t, "\t"));
	}

	private static int count1 = 0;

	public static int binarySearch(int[] a, int target) {

		int low = 0;
		int high = a.length - 1;
		while(low <= high) {
			int mid = (low + high) / 2;

			if(target == a[mid]) {
				return mid;
			} else if(target < a[mid]) {
				high = mid - 1;
				count1++;
			} else {
				low = mid + 1;
				count1++;
			}
		}

		return -1;
	}

}
