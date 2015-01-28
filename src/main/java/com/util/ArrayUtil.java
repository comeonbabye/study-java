package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArrayUtil {

	protected static final Logger log = LoggerFactory.getLogger(ArrayUtil.class);
	
	public static String join(int[] a, String seperate) {
		
		StringBuffer sb = new StringBuffer();
		
		if(a != null && a.length > 0) {
			for(int i : a) {
				sb.append(i).append(seperate);
			}
		}
		return sb.toString();
	}
	
	public static String join(int[] a) {
		
		return join(a, ",");
	}
	 
}
