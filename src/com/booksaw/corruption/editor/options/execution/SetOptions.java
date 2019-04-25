package com.booksaw.corruption.editor.options.execution;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
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

public class SetOptions extends Option implements ActionListener {

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

		JButton add = new JButton(ExecutionSettings.add);
		add.addActionListener(this);
		p.add(add);

		return p;
	}

	@Override
	public void saveData() {

		List<String> commands = new ArrayList<>();
		for (ExecutionOption option : options) {
			String str = option.toSave();
			if (!str.equals(""))
				commands.add(option.toSave());
		}
		if (commands.size() == 0) {
			return;
		}

		set.setExecutions(commands);
		set.save(ref, LevelManager.activeLevel.getSaveManager().config);

	}

	public JComponent getLayer(String command) {
		return new JTextField(command);
	}

	public void replace(ExecutionOption before, ExecutionOption after) {

		options.add(after);
		replace(before, after.getPanel());

	}

	public void replace(ExecutionOption before, JPanel after) {

		options.remove(before);

		int option = 0;
		Component[] list = p.getComponents();
		for (int i = 0; i < list.length; i++) {
			if (before.getPanel() == list[i]) {
				option = i;
				break;
			}
		}
		p.remove(before.getPanel());
		p.add(after, option);
		p.revalidate();
		f.pack();

	}

	public void remove(ExecutionOption toRemove) {

		options.remove(toRemove);
		JPanel panel = toRemove.getPanel();
		p.remove(panel);

		p = new JPanel(new GridLayout(0, 1));
		p.setBorder(new EmptyBorder(10, 10, 10, 10));
		for (ExecutionOption option : options) {
			p.add(option.getPanel());
		}

		JButton add = new JButton(ExecutionSettings.add);
		add.addActionListener(this);
		p.add(add);

		p.revalidate();

		f.pack();

		SetRapper.rapper.check();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ExecutionOption option = CommandList.getExecutionOption(CommandList.KILL, new String[0], f, this);

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
