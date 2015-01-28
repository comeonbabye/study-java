package com.design_pattern.observer.observer_push_model;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//创建主题对象
        Subject subject = new ConcreteSubject();
        //创建观察者对象
        Observer observer = new ConcreteObserver();
        //将观察者对象登记到主题对象上
        subject.attachObserver(observer);
        //改变主题对象的状态
        subject.change("new state");
	}

}
