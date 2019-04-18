package com.booksaw.corruption.editor.options.execution;

import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.Utils;
import com.booksaw.corruption.editor.options.OptionPane;
import com.booksaw.corruption.execution.ExecutionChain;
import com.booksaw.corruption.level.LevelManager;
import com.booksaw.corruption.level.trigger.Trigger;

public class ExecutionSettings extends OptionPane {
	public static final Icon add = new ImageIcon(
			Utils.getImage(new File(Config.ASSETSPATH + File.separator + "addexecute.png")));

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
