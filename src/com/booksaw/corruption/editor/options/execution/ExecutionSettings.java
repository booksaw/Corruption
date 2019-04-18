package com.booksaw.corruption.editor.options.execution;

import com.booksaw.corruption.editor.options.OptionPane;
import com.booksaw.corruption.execution.ExecutionChain;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.trigger.Trigger;

public class ExecutionSettings extends OptionPane {

	Trigger t;

	public ExecutionSettings(Trigger t) {
		super();
		this.t = t;

		intialize();
	}

	@Override
	public String getName() {
		return "Execution Options";
	}

	@Override
	public void loadOptions() {
		included.add(new SetRapper(
				new ExecutionChain("commands." + t.uuid, LevelManager.activeLevel.getSaveManager().config, false), f));
	}

	@Override
	public void deleteThing() {
		t.delete();
	}

}
