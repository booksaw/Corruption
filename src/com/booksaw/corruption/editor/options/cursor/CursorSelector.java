package com.booksaw.corruption.editor.options.cursor;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.booksaw.corruption.editor.options.MessageOption;
import com.booksaw.corruption.editor.options.cursor.CursorSettings.SELECTION;
import com.booksaw.corruption.language.Language;

public class CursorSelector extends MessageOption {

	ButtonGroup b;
	JRadioButton block, background, trigger;

	@Override
	public void saveData() {

		if (block.isSelected()) {
			CursorSettings.selection = SELECTION.BLOCK;
		} else if (background.isSelected()) {
			CursorSettings.selection = SELECTION.BACKGROUND;
		} else if (trigger.isSelected()) {
			CursorSettings.selection = SELECTION.TRIGGER;
		}
	}

	@Override
	public String getTip() {
		return "Select mouse usage";
	}

	@Override
	public JComponent getInput() {
		b = new ButtonGroup();

		JPanel panel = new JPanel(new GridLayout(0, 1));

		block = new JRadioButton(Language.getMessage("cursor.block"));
		b.add(block);
		if (CursorSettings.selection == SELECTION.BLOCK)
			block.setSelected(true);
		panel.add(block);

		background = new JRadioButton(Language.getMessage("cursor.background"));
		b.add(background);
		if (CursorSettings.selection == SELECTION.BACKGROUND)
			background.setSelected(true);
		panel.add(background);

		trigger = new JRadioButton(Language.getMessage("cursor.trigger"));
		b.add(trigger);
		if (CursorSettings.selection == SELECTION.TRIGGER)
			trigger.setSelected(true);
		panel.add(trigger);

		return panel;
	}

}
