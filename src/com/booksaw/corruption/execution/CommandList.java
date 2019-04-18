package com.booksaw.corruption.execution;

import com.booksaw.corruption.execution.commands.CommandKill;
import com.booksaw.corruption.execution.commands.CommandSpeach;
import com.booksaw.corruption.execution.commands.CommandTrigger;

public enum CommandList {

	KILL("kill"), SPEECH("speech"), TRIGGER("trigger");

	String command;

	private CommandList(String command) {
		this.command = command;
	}

	public static Command getCommand(String command, ExecutionSet set) {
		for (CommandList value : CommandList.values()) {
			if (value.command.equals(command)) {
				return getCommand(value, set);
			}
		}

		return null;
	}

	private static Command getCommand(CommandList command, ExecutionSet set) {
		switch (command) {
		case KILL:
			return new CommandKill(set);
		case SPEECH:
			return new CommandSpeach(set);
		case TRIGGER:
			return new CommandTrigger(set);
		}

		return null;
	}

}
