package com.booksaw.corruption.editor.options.sprites;

import javax.swing.JComponent;
import javax.swing.JTextArea;

import com.booksaw.corruption.editor.options.MessageOption;
import com.booksaw.corruption.sprites.Sprite;

public abstract class SpriteOption extends MessageOption {

	Sprite s;
	JTextArea text;

	public SpriteOption(Sprite s) {
		this.s = s;
	}

	public abstract String getTip();

	public abstract String getDefault();

	@Override
	public JComponent getInput() {
		text = new JTextArea();
		text.setText(getDefault());
		return text;
	}
}
