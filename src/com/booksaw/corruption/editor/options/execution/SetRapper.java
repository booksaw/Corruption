package com.booksaw.corruption.editor.options.execution;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.booksaw.corruption.editor.options.Option;
import com.booksaw.corruption.execution.ExecutionChain;
import com.booksaw.corruption.execution.ExecutionSet;
import com.booksaw.corruption.level.LevelManager;

public class SetRapper extends Option implements ActionListener {

	ExecutionChain chain;

	List<SetOptions> options = new ArrayList<>();
	JFrame f;
	JPanel p;

	public SetRapper(ExecutionChain chain, JFrame f) {
		this.chain = chain;
		this.f = f;
	}

	@Override
	public JPanel getPanel() {
		p = new JPanel(new GridLayout(0, 1));

		for (Entry<String, ExecutionSet> sets : chain.getSets().entrySet()) {
			SetOptions options = new SetOptions(sets.getKey(), sets.getValue(), f);
			p.add(options.getPanel());
			this.options.add(options);
		}

		JButton add = new JButton(ExecutionSettings.add);
		add.addActionListener(this);
		p.add(add);

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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String reference = chain.getNextReference();
		SetOptions option = new SetOptions(reference, new ExecutionSet(chain, new ArrayList<>()), f);

		int location = p.getComponents().length - 1;
		if (location < 0) {
			location = 0;
		}

		options.add(option);
		p.add(option.getPanel(), location);
		p.revalidate();
		f.pack();

	}

}