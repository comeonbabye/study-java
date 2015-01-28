package com.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomUtil {

	public static int[] getRandomInt(int size) {

		return getRandomInt(size, 100);
	}

	public static int[] getRandomInt(int size, int seed) {
		Random r = new Random();
		int[] a = new int[size];

		for(int i=0; i<a.length; i++) {
			a[i] = r.nextInt(seed);
		}

		return a;
	}


	public static int[] getRandomIntNoDuplicate(int size) {

		return getRandomIntNoDuplicate(size, 100);
	}

	public static int[] getRandomIntNoDuplicate(int size, int seed) {

		if(size > seed) throw new RuntimeException("size con not big than seed. size:" + size + ", seed:" + seed);

		Random r = new Random();

		Set<Integer> set = new HashSet<Integer>();

		while(set.size() < size) {
			set.add(r.nextInt(seed));
		}

		int[] a = new int[size];
		int index = 0;
		for(Integer i : set) {
			a[index++] = i;
		}

		return a;
	}
}
