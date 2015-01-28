package com.design_pattern.command;

public class BakeMuttonCommand extends Command {

	public BakeMuttonCommand(BarBecuer receive) {
		super(receive);
	}

	@Override
	public void executeCommand() {

		this.receiver.bakeMutton();
	}

}
