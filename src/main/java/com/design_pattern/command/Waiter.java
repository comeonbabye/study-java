package com.design_pattern.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Waiter {

	protected static final Logger log = LoggerFactory.getLogger(BarBecuer.class);

	private List<Command> orders = new ArrayList<Command>();


	public void setOrder(Command command) {

		orders.add(command);
		log.info("添加订单成功:{}", command);

	}

	public void cancelOrder(Command command) {

		orders.remove(command);
		log.info("取消订单成功:{}", command);

	}


	/**
	 * 一次性全部通知
	 *
	 */
	public void notifyBaker() {

		for(Command command : orders) {
			command.executeCommand();
		}
		log.info("执行订单成功:{}", StringUtils.join(orders, "\n"));
	}
}
