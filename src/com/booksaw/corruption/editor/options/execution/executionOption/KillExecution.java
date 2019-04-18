package com.booksaw.corruption.editor.options.execution.executionOption;

import java.awt.GridLayout;
import java.util.UUID;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.booksaw.corruption.editor.options.execution.ExecutionOption;
import com.booksaw.corruption.selection.Selectable;

public class KillExecution extends ExecutionOption {

	public KillExecution(JFrame f, String[] information) {
		super(f, information);
	}

	@Override
	public JPanel getPanel() {
		JPanel p = new JPanel(new GridLayout(1, 0));
		p.add(new JLabel("Kill"));
		selection = Selectable.getSelectable(UUID.fromString(information[0]));
		p.add(getSelectableSelector());

		return p;
	}

	@Override
	public String toSave() {
		return "kill:" + selection.uuid;
	}

}
