package com.booksaw.corruption.editor.options;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;

import com.booksaw.corruption.language.Language;

public abstract class ColorPicker extends MessageOption implements ActionListener {

	private JButton colorChooser;
	protected Color c;
	private JFrame f;

	public ColorPicker(JFrame f) {
		this.f = f;
		c = Color.BLACK;
	}

	@Override
	public JComponent getInput() {

		colorChooser = new JButton("...");
		colorChooser.addActionListener(this);
		colorChooser.setToolTipText(Language.getMessage("editor.color"));

		return colorChooser;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Color temp = JColorChooser.showDialog(f, Language.getMessage("editor.color.title"), c);

		if (temp != null) {
			c = temp;
		}

	}

}
