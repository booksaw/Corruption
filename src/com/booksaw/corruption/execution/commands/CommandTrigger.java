package com.booksaw.corruption.execution.commands;

import java.util.Arrays;
import java.util.UUID;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.execution.Command;
import com.booksaw.corruption.execution.ExecutionSet;
import com.booksaw.corruption.selection.Selectable;

public class CommandTrigger extends Command {

	public CommandTrigger(ExecutionSet set) {
		super(set);
	}

	@Override
	public void execute(String command, String[] args) {
		Selectable s = Selectable.getSelectable(UUID.fromString(args[0]));
		try {
			s.trigger(Config.removeFirstElement(args));
		} catch (Exception e) {
			System.out.println("ERROR IN COMMAND: " + command + "args:  " + Arrays.toString(args));
		}
	}

}
