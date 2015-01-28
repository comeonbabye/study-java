package com.design_pattern.command;

public class BakeChickenWingCommand extends Command {

	public BakeChickenWingCommand(BarBecuer receive) {
		super(receive);
	}

	@Override
	public void executeCommand() {

		this.receiver.bakeChickenWing();
	}

}
