package com.booksaw.corruption.editor.options.player;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import com.booksaw.corruption.editor.options.MessageOption;
import com.booksaw.corruption.sprites.Sprite;

public class ActiveSprite extends MessageOption {

	Sprite s;
	JCheckBox box;

	public ActiveSprite(Sprite s) {
		this.s = s;
		box = new JCheckBox();
		box.setSelected(s.activePlayer);
	}

	@Override
	public void saveData() {
		s.activePlayer = box.isSelected();
	}

	@Override
	public String getTip() {
		return "Set active player";
	}

	@Override
	public JComponent getInput() {
		return box;
	}

}
