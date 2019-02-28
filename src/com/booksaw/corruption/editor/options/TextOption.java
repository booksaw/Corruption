package com.booksaw.corruption.editor.options;

import javax.swing.JComponent;
import javax.swing.JTextArea;

public abstract class TextOption extends MessageOption {

	protected JTextArea text;


	public abstract String getTip();

	public abstract String getDefault();

	@Override
	public JComponent getInput() {
		text = new JTextArea();
		text.setText(getDefault());
		return text;
	}
}
