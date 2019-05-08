package com.booksaw.corruption.controls;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Control implements ActionListener, KeyListener {

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

	JButton b;

	private JButton getKeybindButton(int ref) {
		b = new JButton(((char) ref) + "");
		b.addActionListener(this);
		return b;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (b != null) {
			b.setText("");
			b.addKeyListener(this);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		b.setText(((char) e.getKeyCode()) + "");

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	public void save() {
		if (b != null && b.getText().length() != 0)
			keys[0] = b.getText().charAt(0);
	}

}
