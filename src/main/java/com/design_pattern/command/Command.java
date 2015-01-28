package com.design_pattern.command;

import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class Command {

	protected BarBecuer receiver;

	public Command(BarBecuer receiver) {
		this.receiver = receiver;
	}

	abstract public void executeCommand();

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
