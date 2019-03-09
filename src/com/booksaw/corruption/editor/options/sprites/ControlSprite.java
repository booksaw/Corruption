package com.booksaw.corruption.editor.options.sprites;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import com.booksaw.corruption.editor.options.MessageOption;
import com.booksaw.corruption.sprites.Sprite;

public class ControlSprite extends MessageOption {

	Sprite s;
	JCheckBox box;

	public ControlSprite(Sprite s) {
		this.s = s;
		box = new JCheckBox();
		box.setSelected(s.activePlayer);
	}

	@Override
	public void saveData() {
		s.controllable = box.isSelected();
	}

	@Override
	public String getTip() {
		return "Set controllable";
	}

	@Override
	public JComponent getInput() {
		return box;
	}

}
