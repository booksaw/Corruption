package com.booksaw.corruption.controls;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.booksaw.corruption.settings.Settings;

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
		b = new JButton(getKeyString(ref));
		b.addActionListener(this);
		return b;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (b != null) {
			Settings.settings.clearButtons();
			b.setText("");
			b.addKeyListener(this);

		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		String s = getKeyString(e.getKeyCode());
		if (!s.equals("")) {
			b.setText(s);
		} else {
			b.setText(getKeyString(keys[0]));
		}

		b.removeKeyListener(this);
	}

	public void press() {
		if (b != null) {
			if (!b.getText().equals("")) {
				return;
			}

			b.setText(getKeyString(keys[0]));

			b.removeKeyListener(this);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	public void save() {
		if (b != null && b.getText().length() != 0)
			keys[0] = getKeyInt(b.getText());

	}

	private String getKeyString(int code) {

//		System.out.println("text = " + code);

		// if they have used a number on the numpad
		if (code >= 96 && code <= 105) {
			return (code - 96) + "";
		}

		// f keys
		if (code >= 112 && code <= 123) {
			return "F" + (code - 111);
		}

		switch (code) {
		case 17:
		case 27:
		case 524:
			return "";
		case 37:
			return "left";
		case 39:
			return "right";
		case 38:
			return "up";
		case 40:
			return "down";
		case 10:
			return "enter";
		case 16:
			return "shift";
		case 20:
			return "caps lock";
		case 18:
			return "alt";
		case 8:
			return "backspace";
		case 192:
			return "grave";
		case 32:
			return "space";
		case 525:
			return "R click";
		case 127:
			return "delete";
		case 35:
			return "end";
		case 34:
			return "page down";
		case 36:
			return "home";
		case 33:
			return "page up";
		case 155:
			return "insert";
		case 144:
			return "num lock";
		case 111:
			return "/";
		case 106:
			return "*";
		case 109:
			return "-";
		case 107:
			return "+";
		case 110:
			return ".";

		default:
			return (char) code + "";
		}

	}

	private int getKeyInt(String text) {
		switch (text) {
		case "left":
			return 37;
		case "right":
			return 39;
		case "up":
			return 38;
		case "down":
			return 40;
		case "enter":
			return 10;
		case "shift":
			return 10;
		case "caps lock":
			return 20;
		case "alt":
			return 18;
		case "backspace":
			return 8;
		case "grave":
			return 192;
		case "space":
			return 32;
		case "R click":
			return 525;
		case "delete":
			return 127;
		case "end":
			return 35;
		case "page down":
			return 34;
		case "home":
			return 36;
		case "page up":
			return 33;
		case "insert":
			return 155;
		case "num lock":
			return 14;
		case "/":
			return 111;
		case "*":
			return 106;
		case "-":
			return 109;
		case "+":
			return 107;
		case ".":
			return 110;
		case "F1":
			return 112;
		case "F2":
			return 113;
		case "F3":
			return 114;
		case "F4":
			return 115;
		case "F5":
			return 116;
		case "F6":
			return 117;
		case "F7":
			return 118;
		case "F8":
			return 119;
		case "F9":
			return 120;
		case "F10":
			return 121;
		default:
			return text.charAt(0);
		}

	}

	public void clear() {
		press();
	}

}
