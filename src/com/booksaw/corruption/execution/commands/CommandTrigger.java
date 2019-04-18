package com.booksaw.corruption.execution.commands;

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
		System.out.println(args[0]);
		Selectable s = Selectable.getSelectable(UUID.fromString(args[0]));
		s.trigger(Config.removeFirstElement(args));
	}

}
