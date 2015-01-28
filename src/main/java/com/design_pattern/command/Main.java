package com.design_pattern.command;

/**
 * 命令模式：将一个请求封装为一个对象，从而使你可以用不同的请求对客户进行参数化，对请求排队和记录请求日志，以及支持可撤销的操作。
 * 应用场景：将命令者与执行者完全解耦。
 * @author tony
 *
 */
public class Main {

	public static void main(String[] args) {

		BarBecuer boy = new BarBecuer();
		Command bakeMuttonCommand1 = new BakeMuttonCommand(boy);
		Command bakeMuttonCommand2 = new BakeMuttonCommand(boy);
		Command bakeChickenWingCommand1 = new BakeChickenWingCommand(boy);

		Waiter girl =new Waiter();
		girl.setOrder(bakeMuttonCommand1);
		girl.setOrder(bakeMuttonCommand2);
		girl.setOrder(bakeChickenWingCommand1);
		girl.notifyBaker();

	}
}
