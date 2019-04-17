package com.booksaw.corruption.execution;

import com.booksaw.corruption.Config;

public abstract class Command {

	public static Command runCommand(String ref, ExecutionSet set) {

		String[] split = ref.split(":");
		// split[0] = command reference

		String[] args = Config.removeFirstElement(split);

		Command cmd = CommandList.getCommand(split[0], set);

		cmd.execute(split[0], args);

		return cmd;
	}

	private boolean complete = false;
	protected ExecutionSet set;

	public Command(ExecutionSet set) {
		this.set = set;
	}

	public void setComplete() {
		complete = true;
		set.complete();
	}

	public boolean isComplete() {
		return complete;
	}

	public abstract void execute(String command, String[] args);
}
