package com.booksaw.corruption.editor.options;

import javax.swing.JPanel;

public abstract class Option {

	public abstract JPanel getPanel();
	public abstract void saveData();

}
