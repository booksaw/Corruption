package com.booksaw.corruption.controls;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Control implements ActionListener {

	String name;
	int[] keys;

	public Control(String name, String string) {

		String[] split = string.split(";");

		keys = new int[split.length];

		for (int i = 0; i < split.length; i++) {
			keys[i] = Integer.parseInt(split[i]);
		}

		this.name = name;
	}

	@Override
	public String toString() {

		String toReturn = "";

		for (int i = 0; i < keys.length; i++) {
			toReturn = toReturn + keys[i] + ((keys.length - 1 == i) ? "" : ";");
		}

		return toReturn;
	}

	public JPanel getPanel() {

		JPanel p = new JPanel(new GridLayout(1, 0));

		String[] split = name.split("\\.");
		p.add(new JLabel(" " + split[split.length - 1]));
		p.add(getKeybindButton(keys[0]));
		return p;
	}

	private JButton getKeybindButton(int ref) {
		JButton b = new JButton(((char) ref) + "");
		b.addActionListener(this);
		return b;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
