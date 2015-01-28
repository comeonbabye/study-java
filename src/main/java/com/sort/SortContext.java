package com.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * 创建日期:2015-1-6
 * Description：策略模式上下文
 * @author tony.he
 * @version 1.0
 * @param <T>
 */
public class SortContext<T> {

	protected static final Logger log = LoggerFactory.getLogger(SortContext.class);
	
	private SortStrategy<T> sortStrategy;  
    
    public SortContext(SortStrategy<T> sortStrategy) {  
        this.sortStrategy = sortStrategy;  
    }  
    
    
    public void sort(T t) { 
    	
    	//sortStrategy.printBefore(t);
    	
    	long start = System.currentTimeMillis();
        this.sortStrategy.sort(t);
        log.info("sort elapsed time:{}", (System.currentTimeMillis() - start));
        
        
        //sortStrategy.printAfter(t);
    }  
}
