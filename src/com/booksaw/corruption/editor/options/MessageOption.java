package com.booksaw.corruption.editor.options;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class MessageOption extends Option {

	@Override
	public JPanel getPanel() {
		JPanel p = new JPanel(new GridLayout(1, 0));
//		JPanel toCentre = new JPanel(new BorderLayout());
		p.add(new JLabel(" " + getTip()));

		p.add(getInput());

		return p;
	}

	@Override
	public abstract void saveData();

	public abstract String getTip();

	public abstract JComponent getInput();
}
