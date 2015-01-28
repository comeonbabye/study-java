package com.sort;

public abstract class SortStrategy<T> {
	
	public abstract void printBefore(T t); 
	
	public abstract void sort(T t);  
	
	public abstract void printAfter(T t); 
}
