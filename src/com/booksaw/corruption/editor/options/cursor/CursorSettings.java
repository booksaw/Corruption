package com.booksaw.corruption.editor.options.cursor;

import com.booksaw.corruption.editor.options.OptionPane;

public class CursorSettings extends OptionPane {

	public static SELECTION selection = SELECTION.BLOCK;

	public enum SELECTION {
		BLOCK, BACKGROUND, SELECTOR, TRIGGER/* , OBJECT */;
	}

	@Override
	public String getName() {
		return "Select cursor use";
	}

	@Override
	public void loadOptions() {
		deletable = false;

		included.add(new CursorSelector());
	}

	@Override
	public void deleteThing() {
	}

}
