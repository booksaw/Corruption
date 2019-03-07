package com.booksaw.corruption.editor.options.cursor;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.booksaw.corruption.editor.options.MessageOption;
import com.booksaw.corruption.editor.options.cursor.CursorSettings.SELECTION;
import com.booksaw.corruption.language.Language;
import com.booksaw.corruption.render.overlays.DoorOverlay;
import com.booksaw.corruption.render.overlays.Overlay;

public class CursorSelector extends MessageOption {

	ButtonGroup b;
	JRadioButton block, background, door;

	@Override
	public void saveData() {
		if (CursorSettings.selection == SELECTION.DOOR) {
			Overlay.removeOverlay(DoorOverlay.doorOverlay);
		}

		if (block.isSelected()) {
			CursorSettings.selection = SELECTION.BLOCK;
		} else if (background.isSelected()) {
			CursorSettings.selection = SELECTION.BACKGROUND;
		} else if (door.isSelected()) {
			CursorSettings.selection = SELECTION.DOOR;
			Overlay.addOverlay(new DoorOverlay());
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

		door = new JRadioButton(Language.getMessage("cursor.door"));
		b.add(door);
		if (CursorSettings.selection == SELECTION.DOOR)
			door.setSelected(true);
		panel.add(door);

		return panel;
	}

}
