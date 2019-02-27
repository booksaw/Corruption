package com.booksaw.corruption.editor.options.gameobjects;

import javax.swing.JComponent;
import javax.swing.JTextArea;

import com.booksaw.corruption.editor.options.MessageOption;
import com.booksaw.corruption.level.objects.Block;

public abstract class BlockOption extends MessageOption {

	Block b;
	JTextArea text;

	public BlockOption(Block b) {
		this.b = b;
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
