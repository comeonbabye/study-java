package com.util;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PathUtil {

	
	protected static final Logger log = LoggerFactory.getLogger(PathUtil.class);
	
	public static void main(String[] args) {
		
		log.info("获取当前类的所在工程路径，bin目录：{}", PathUtil.class.getResource("/").getPath());
		log.info("获取当前类的绝对路径：{}", PathUtil.class.getResource("").getPath());
		log.info("获取当前类的所在工程路径：{}", System.getProperty("user.dir"));
		log.info("获取当前类的所在工程路径：{}", System.getProperty("java.class.path"));
		
		URL xmlpath = PathUtil.class.getClassLoader().getResource(""); 
		log.info("获取当前类的所在工程路径：{}", xmlpath.getPath());
		
	}
	
}
