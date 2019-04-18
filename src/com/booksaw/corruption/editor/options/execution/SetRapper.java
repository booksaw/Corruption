package com.booksaw.corruption.editor.options.execution;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.booksaw.corruption.editor.options.Option;
import com.booksaw.corruption.execution.ExecutionChain;
import com.booksaw.corruption.execution.ExecutionSet;
import com.booksaw.corruption.level.LevelManager;

public class SetRapper extends Option {

	ExecutionChain chain;

	List<SetOptions> options = new ArrayList<>();
	JFrame f;

	public SetRapper(ExecutionChain chain, JFrame f) {
		this.chain = chain;
		this.f = f;
	}

	@Override
	public JPanel getPanel() {
		JPanel p = new JPanel(new GridLayout(0, 1));

		for (Entry<String, ExecutionSet> sets : chain.getSets().entrySet()) {
			SetOptions options = new SetOptions(sets.getKey(), sets.getValue(), f);
			p.add(options.getPanel());
			this.options.add(options);
		}

		return p;
	}

	@Override
	public void saveData() {

		LevelManager.activeLevel.getSaveManager().changes(true);

		for (SetOptions t : options) {
			t.saveData();
		}

		LevelManager.activeLevel.getSaveManager().config.saveConfiguration();

	}

}
