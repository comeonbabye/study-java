package com.design_pattern.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BarBecuer {

	protected static final Logger log = LoggerFactory.getLogger(BarBecuer.class);


	public void bakeMutton() {
		log.info("烤羊肉串！");
	}

	public void bakeChickenWing() {
		log.info("烤鸡翅！");
	}
}
