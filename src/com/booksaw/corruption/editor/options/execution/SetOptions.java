package com.booksaw.corruption.editor.options.execution;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.booksaw.corruption.Config;
import com.booksaw.corruption.editor.options.Option;
import com.booksaw.corruption.execution.CommandList;
import com.booksaw.corruption.execution.ExecutionSet;
import com.booksaw.corruption.level.LevelManager;

public class SetOptions extends Option {

	ExecutionSet set;
	String ref;
	JFrame f;

	public SetOptions(String ref, ExecutionSet set, JFrame f) {
		this.set = set;
		this.ref = ref;
		this.f = f;
	}

	List<ExecutionOption> options = new ArrayList<>();

	JPanel p;

	// dont forget set preffered size
	@Override
	public JPanel getPanel() {
		p = new JPanel(new GridLayout(0, 1));
		p.setBorder(new EmptyBorder(10, 10, 10, 10));
		for (String str : set.getExecutions()) {
			String[] split = str.split(":");
			ExecutionOption option = CommandList.getExecutionOption(split[0], Config.removeFirstElement(split), f,
					this);
			options.add(option);
			p.add(option.getPanel());
		}

		return p;
	}

	@Override
	public void saveData() {

		List<String> commands = new ArrayList<>();

		for (ExecutionOption option : options) {
			commands.add(option.toSave());
		}

		set.setExecutions(commands);
		set.save(ref, LevelManager.activeLevel.getSaveManager().config);

	}

	public JComponent getLayer(String command) {
		return new JTextField(command);
	}

	public void replace(ExecutionOption before, ExecutionOption after) {

		options.remove(before);
		options.add(after);

		int option = 0;
		Component[] list = p.getComponents();
		for (int i = 0; i < list.length; i++) {
			if (before.getPanel() == list[i]) {
				option = i;
				break;
			}
		}
		p.remove(before.getPanel());
		p.add(after.getPanel(), option);
		p.revalidate();
		f.pack();

	}

}
