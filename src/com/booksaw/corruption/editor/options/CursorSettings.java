package com.booksaw.corruption.editor.options;

import com.booksaw.corruption.editor.options.cursor.CursorSelector;

public class CursorSettings extends OptionPane {

	public static SELECTION selection = SELECTION.BLOCK;

	public enum SELECTION {
		BLOCK, BACKGROUND;
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
